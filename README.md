# MySnackbar

     allprojects {
	     	repositories {
		     	...
	  		  maven { url 'https://jitpack.io' }
		  }
   	}
    
       dependencies {
	        implementation 'com.github.developeramit3:MySnackbar:3a6f8ccd96'
	  }
  
  
  [![](https://jitpack.io/v/developeramit3/MySnackbar.svg)](https://jitpack.io/#developeramit3/MySnackbar)
  
  
  # How to Use
  
       MySnackbar.with(MainActivity.this,view).type(Type.ERROR)
                        .message("Replace with your own action")
                        .duration(Duration.LONG).show();
