# 一、Unity3dPlugin简介
致力于提供一套最基础的android和unity3d交互的插件方案，精简封装android端与unity3d交互的api，降低android开发者接入unity3d的成本。当然，使用的前提是你必须有一定的unity3d基础知识。

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



	@PermissionNotify
	public class MainActivity extends AppCompatActivity implements View.OnClickListener
		private TextView mTextMessage;
		protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        mTextMessage = (TextView) findViewById(R.id.message);
	
	        mTextMessage.setOnClickListener(this);
    	}
    	
	    private void setText(final String text) {
	        mTextMessage.setText(text);
	    }
	
	    @Override
	    public void onClick(View v) {
	        setText("哈哈哈哈哈哈");
	    }
	}



然后在Activity或Fragment的某个方法上加上注解@PermissionRequest




	  @PermissionRequest(
            requestCode = 10010,
            requestPermissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_CONTACTS}
                    ,needReCall = true
    )
    private void setText(String text) {
        mTextMessage.setText(text);
    }



然后。。。。。。结束啦！！！！编译结束后其实MainActivity里的代码大致会变成如下的样子



	@PermissionNotify
	public class MainActivity extends AppCompatActivity implements OnClickListener, PermissionsRequestCallback {
	    private TextView mTextMessage;
	   	 private final Map requestPermissionMethodParams = new HashMap();
	
	    public MainActivity() {
	    }
	
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        this.setContentView(2131361818);
	       
	        this.mTextMessage.setOnClickListener(this);
	    }
	
	    @PermissionRequest(
	        requestCode = 10010,
	        requestPermissions = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.READ_CONTACTS"},
	        needReCall = true
	    )
	    private void setText(String text) {
	        String[] var2 = new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.READ_CONTACTS"};
	        boolean var3 = PermissionsManager.getInstance().hasAllPermissions(this, var2);
	        if (!var3) {
	            ArrayList var4 = new ArrayList();
	            var4.add(text);
	            this.requestPermissionMethodParams.put(10010, var4);
	            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(10010, this, var2, this);
	        } else {
	            this.mTextMessage.setText(text);
	        }
	    }
	
	    public void onClick(View v) {
	        this.setText("哈哈哈哈哈哈");
	    }
	
	    public void onGranted(int var1, String var2) {
	    }
	
	    public void onDenied(int var1, String var2) {
	    }
	
	    public void onDeniedForever(int var1, String var2) {
	    }
	
	    public void onFailure(int var1, String[] var2) {
	    }
	
	    public void onSuccess(int var1) {
	        Object var2 = this.requestPermissionMethodParams.get(var1);
	        if (var1 == 10010) {
	            this.setText((String)((List)var2).get(0));
	        }
	    }
	
	    public void onRequestPermissionsResult(int var1, String[] var2, int[] var3) {
	        PermissionsManager.getInstance().notifyPermissionsChange(var2, var3);
	        super.onRequestPermissionsResult(var1, var2, var3);
	    }
	}






当然，如果你觉得使用注解会有诸多限制（请看下面第四条提到的“一些限制”）,你也可以直接使用simplepermission库来实现权限的申请，类似代码如下




	private void setText(final String text) {
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(0, this,
                new String[]{Manifest.permission.READ_CONTACTS}, new PermissionsRequestCallback() {
                    @Override
                    public void onGranted(int requestCode, String permission) {
                        
                    }

                    @Override
                    public void onDenied(int requestCode, String permission) {

                    }

                    @Override
                    public void onDeniedForever(int requestCode, String permission) {

                    }

                    @Override
                    public void onFailure(int requestCode, String[] deniedPermissions) {

                    }

                    @Override
                    public void onSuccess(int requestCode) {
                        mTextMessage.setText(text);
                    }
                });
    }
	


对于一些特殊权限，可能需要在Activit或Fragment重载onRequestPermissionsResult方法，并且在该方法内部加入PermissionsManager.getInstance().notifyPermissionsChange(permissions,grantResults),类似如下代码



	@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions,grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }




# 四、一些限制

##### 4.1 由于本人能力有限，本库目前还不支持在“带返回值”的方法上去加@PermissionRequest注解并且@PermissionRequest的needRecall设置为true，当然，needRecall设置为false时，是支持的。因为本库的原理是这样的：添加了@PermissionRequest注解的方法，我会先判断当前程序是否已经有了@PermissionRequest的requestPermissions里包含的权限，如果没有，则插入申请权限的代码，如果@PermissionRequest的needRecall为true，则需要存储方法的参数，以备权限回调成功的时候在调用此方法，然后插入“return”，结束方法执行；如果@PermissionRequest的needRecall为false，则这里只插入申请权限的代码，不在干预此方法的后续逻辑。

##### 4.2 本库目前还不支持在内部类里面的方法上加@PermissionRequest注解，因为permission权限申请库必要的一个参数是Activit或Fragment，如果是在内部类里面使用，我目前还无法得知如何获取该内部类持有的Activit或Fragment，尤其是在多层内部类嵌套的时候。

##### 4.3 本库因为修改了class文件插入了一些代码，很有可能会使应用程序出现multiDex异常，所以，在需要的时候，最好让你的程序支持multiDex


## License

'

	Copyright 2018 ykbjson
	
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


