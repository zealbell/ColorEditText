[![Project Status: Active - Initial development has started, usable release; work hasn't been stopped ](http://www.repostatus.org/badges/0.1.0/active.svg)](http://www.repostatus.org/#active)

ColorEditText
=============
A custom EditText-view for having texts of multiple colors in the same EditText-view with a single xml injection call plus an inclusive call to dismiss keyboard or pan/adjust it’s height above the keyboard.
## Synopsis
This Library consists of three Views:
  1. **ColorEditText(EditText$SoftEditText)** here you can customise the EditText dynamically(JAVA) or statically(XML)
> *public-methods*
```java
       public void setText (String textStatement, String colorStatement, boolean textIsHint)
```
  2. **SoftEditText(EditText)** the parent class of the ColorEditText and has the following methods
> *public-methods*
```java
       public void CallKeyboard()
       public void DismissKeyboard()
       public void setPanner(int panHeight,int pannerId) //call this if view is in activity's root-view
       public void setPanner(int panHeight,View pannerView) //call if view is not / you have a complex positioning of the EditText
       public void setkeyboardListener(SoftKeyboard.keyboardListener listener)
       public void removekeyboardListener()
```
  3. **SoftKeyboard** which  hosts the keyboardListener
> *public-methods*
```java
  setImeOptions(ColorEditText ColEx, String type) //pass in type from 'SoftKeyboard.Type'
```

## Quick Start
> Activity
 Have this in your activity if you specified panning statically/dynamically.
```java
context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
```
> Gradle
```xml
   dependencies {
      compile 'com.github.54LiNKeR:ColorEditText:1.0.0'
   }
```
> Layout
```xml
  <linkersoft.blackpanther.text.ColorEditText
         android:layout_width="wrap_content"
         android:layout_height="wrap_content"
         android:textColor="#f53457"
         android:textSize="14sp"
         app:Colours="#F53457:#A31730:#00a0b4"
         app:Sentence="Text 1 # Bold Text2% # Text3"
         app:TextIsHint="false"
         app:panHeight="12"
         app:pannerId="@+id/pan"
         tools:ignore="RtlSymmetry" />

```

