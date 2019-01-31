# 一、Unity3dPlugin简介
致力于提供一套最基础的android和unity3d交互的插件方案，精简封装android端与unity3d交互的api，降低android开发者接入unity3d的成本。当然，使用的前提是你必须有一定的unity3d基础知识。

[关于Unity3dPlugin详细信息]()

# 二、如何引用
当前最新版本为
[ ![Download](https://api.bintray.com/packages/ykbjson/maven/unity3dplugin/images/download.svg) ](https://bintray.com/ykbjson/maven/unity3dplugin/_latestVersion)

>gradle

在app module的build.gradle文件里引入最新版本即可(_latestVersion请看上方图标里的数字)


	implementation 'com.ykbjson.unity3d:unity3dplugin: _latestVersion'
 

>maven

	<dependency>
	  <groupId>com.ykbjson.unity3d</groupId>
	  <artifactId>unity3dplugin</artifactId>
	  <version>1.0.0</version>
	  <type>pom</type>
	</dependency>	


# 三、如何使用

Activity直接加载Unity3d视图

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
                .callModelName("Ball")//对应的unity组件挂在的script文件指定的名字
                .callMethodName("SetPause")//对应的unity组件挂在的script文件里的方法名字
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
	        Toast.makeText(this, "来自Unity的消息: "+message, Toast.LENGTH_SHORT).show();
	    }
	}
	
	
因为UnityPlayerActivity实现了IOnUnity3DCall接口，所以重载onVoidCall 、onReturnCall方法即可收到unity发送来的消息，然后自己处理；重载unityPlayerContainerId方法返回有效的可以加载UnityPlayer的ViewGroup的id，然后就完成啦。Activity呈现unity场景就是这么简单。

至于Fragment，其实也很简单，大家可以参照unity3dplugin这个库工程里的UnityPlayerFragment的代码，然后在承载这个Fragment的Activity（必须继承至UnityPlayerActivity或者参照UnityPlayerActivity实现）的generateIOnUnity3DCallDelegate方法里，返回你自定义的满足要求的（实现IOnUnity3DCall、IUnityPlayerContainer接口，或者继承至UnityPlayerFragment）Fragment即可，其他地方无需改动。

## License

'

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


'


