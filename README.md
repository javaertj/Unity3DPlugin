# 一、Unity3dPlugin简介
致力于提供一套最基础的android和unity3d交互的插件方案，精简封装android端与unity3d交互的api，降低android开发者接入unity3d的成本。当然，使用的前提是你必须有一定的unity3d基础知识。

[Unity3dPlugin解析CSDN](https://blog.csdn.net/yankebin/article/details/86715053)

[Unity3dPlugin解析github blog](https://ykbjson.github.io/2019/01/30/Unity%E4%B8%8EAndroid%E9%80%9A%E4%BF%A1%E7%9A%84%E4%B8%AD%E9%97%B4%E4%BB%B6(%E4%B8%80)/)

关于unity端的源码，请看下面：

>[demo依赖的unity工程-unity3dballgame](https://code.aliyun.com/modelingwithunity3d/unity3dballgame.git)


# 二、如何引用
当前最新版本为
[ ![Download](https://api.bintray.com/packages/ykbjson/maven/unity3dplugin/images/download.svg) ](https://bintray.com/ykbjson/maven/unity3dplugin/_latestVersion)

首先，要在项目根目录的build文件对应的地方加入以下信息

	 repositories {
	      mavenCentral()
	      maven { url 'https://dl.bintray.com/ykbjson/maven' }	    }


	 allprojects {
	    repositories {
	     	mavenCentral()
	      maven { url 'https://dl.bintray.com/ykbjson/maven' }
	    }
	}
	
	
然后在app module的build.gradle文件里引入最新版本(_latestVersion请看上方图标里的数字)


	implementation 'com.ykbjson.unity3d:unity3dplugin: _latestVersion'
 

# 三、如何使用

想要快速看到效果？

直接clone整个项目，用androidstudio导入编译运行即可。

又或者你想自己重头开始

1.建好自己的unity工程，编译并导出为android project备用，关于unity如何导出为android工程，请自行google。

2.建好普通android工程，把上一步unity导出的android工程里src/main/assets目录拷贝到你新建android工程的src/main目录下。

3.Activity直接加载Unity3d视图，继承至UnityPlayerActivity即可

	public class MainActivity extends UnityPlayerActivity {
	
	    private boolean isPause;
	    @BindView(R.id.buttonPause)
	    Button buttonPause;
	
	    @Override
	    public int contentViewLayoutId() {
	        return R.layout.activity_main;
	    }
	
	
	    @Override
	    public void onVieDestroyed() {
	
	    }
	
	    //unity3d发送过来的消息，不需要返回值
	    @Override
	    public void onVoidCall(@NonNull ICallInfo callInfo) {
	        switch (callInfo.getCallMethodName()) {
	            case "showToast":
	                showToast(callInfo.getCallMethodParams().getString("message"));
	                break;
	
	            default:
	                break;
	        }
	    }
	
	    //unity3d发送过来的消息，需要返回值
	    @Override
	    public Object onReturnCall(@NonNull ICallInfo callInfo) {
	        return null;
	    }
	
	    @Override
	    public int unityPlayerContainerId() {
	        return R.id.unityPlayerContainer;
	    }
	    
		 @Nullable
	    @Override
	    protected IOnUnity3DCall generateIOnUnity3DCallDelegate(@NonNull UnityPlayer unityPlayer, 		@Nullable Bundle bundle) {
	        return super.generateIOnUnity3DCallDelegate(unityPlayer, bundle);
	    }
	
	    public void setPause(View view) {
	        isPause = !isPause;
	        buttonPause.setText(isPause ? "继续" : "暂停");
	        CallInfo.Builder
                .create()
                .callModelName("Ball")//对应的是unity组件挂载的script文件指定的名字
                .callMethodName("SetPause")//对应的是unity组件挂载的script文件里的方法名字
                .addCallMethodParam("isPause", isPause)////对应的是unity组件挂在的script文件里的方法需要的参数
                .build()
                .send();
	    }
	
	    /**
	     * 显示一个toast
	     *
	     * @param message
	     */
	    private void showToast(String message) {
	        Toast.makeText(this, "来自Unity的消息: "+message, Toast.LENGTH_SHORT).show();
	    }
	}
	
	
因为UnityPlayerActivity实现了IOnUnity3DCall接口，所以重载onVoidCall 、onReturnCall方法即可收到unity发送来的消息，然后自己处理；重载unityPlayerContainerId方法返回有效的可以加载UnityPlayer的ViewGroup的id，然后就完成啦。Activity呈现unity场景就是这么简单。

4.Fragment载Unity3d视图，其实也很简单，可以参照unity3dplugin这个库工程里的UnityPlayerFragment的代码，然后在承载这个Fragment的Activity（必须继承至UnityPlayerActivity或者参照UnityPlayerActivity实现）的generateIOnUnity3DCallDelegate方法里，返回你自定义的满足要求的（实现IOnUnity3DCall、IUnityPlayerContainer接口，或者继承至UnityPlayerFragment）Fragment即可，其他地方无需改动


    public class MainUnityPlayFragment extends BaseFragmentV4 implements IOnUnity3DCall, IUnityPlayerContainer, View.OnClickListener {
        protected UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code
    
        private boolean isPause;
        @BindView(R.id.buttonPause)
        Button buttonPause;
    
        @Override
        public int contentViewLayoutId() {
            return R.layout.fragment_show_unity_palyer;
        }
    
        @Override
        @CallSuper
        public void onViewCreated(@NonNull Bundle params, @NonNull View contentView) {
            if (null != mUnityPlayer) {
                final ViewGroup unityContainer = contentView.findViewById(unityPlayerContainerId());
                unityContainer.addView(mUnityPlayer);
                mUnityPlayer.requestFocus();
            }
            buttonPause.setOnClickListener(this);
        }
    
        @Override
        public void onVieDestroyed() {
    
        }
    
        @Override
        public void onVoidCall(@NonNull ICallInfo callInfo) {
            switch (callInfo.getCallMethodName()) {
                case "showToast":
                    showToast(callInfo.getCallMethodParams().getString("message"));
                    break;
    
                default:
                    break;
            }
        }
    
        @Override
        public Object onReturnCall(@NonNull ICallInfo callInfo) {
            return null;
        }
    
        @Nullable
        @Override
        public Context gatContext() {
            return getActivity();
        }
    
        @Override
        public int unityPlayerContainerId() {
            return R.id.unityPlayerContainer;
        }
    
        public void setUnityPlayer(@NonNull UnityPlayer mUnityPlayer) {
            this.mUnityPlayer = mUnityPlayer;
        }
    
        @Override
        public void onClick(View view) {
            isPause = !isPause;
            buttonPause.setText(isPause ? "继续" : "暂停");
            CallInfo.Builder
                    .create()
                    .callModelName("Ball")//对应的unity组件挂在的script文件指定的名字,本demo中对应BallController
                    .callMethodName("SetPause")//对应的unity组件挂在的script文件里的方法名字，本demo中对应BallController的SetPause方法
                    .addCallMethodParam("isPause", isPause)////对应的unity组件挂在的script文件里的方法需要的参数
                    .build()
                    .send();
        }
    
        /**
         * 显示一个toast
         *
         * @param message
         */
        private void showToast(String message) {
            Toast.makeText(getActivity(), "来自Unity的消息: " + message, Toast.LENGTH_SHORT).show();
        }
    
        public static MainUnityPlayFragment instantiate(Context context, String name, Bundle param, UnityPlayer unityPlayer) {
            final MainUnityPlayFragment mainUnityPlayFragment = (MainUnityPlayFragment) Fragment.instantiate(context, name, param);
            mainUnityPlayFragment.setUnityPlayer(unityPlayer);
            return mainUnityPlayFragment;
        }
    }



# 小贴士

1.自己实现对接时，请注意参考demo的AndroidManifest文件和build文件，根据自己的项目适当取舍。

2.由于unity版本不同，所导出的android project会有所不同，运行的时候会提示版本不同的错误，大家用自己的unity编辑器导入demo所依赖的unity工程的时候需谨慎。我所尝试过的方法就是把unity编辑器导出的android工程的assets目录完全覆盖当前android工程的assets目录，clean project，然后在运行。

# License

	Copyright 2019 ykbjson
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	   http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.	




