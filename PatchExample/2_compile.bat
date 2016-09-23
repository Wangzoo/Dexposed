::将src下的文件路径存在src.list文件中
::find src name *.java > src.list
 
::将gen下的文件路径存在gen.list文件中
::find gen name *.java > gen.list 

mkdir bin

javac -target 1.6 -bootclasspath D:/android-sdk-windows/platforms/android-17/android.jar -d bin gen\com\example\antdemo\*.java src\com\example\antdemo\*.java