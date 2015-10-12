#FlycoPageIndicator

A Page Indicator Lib is realized in a different way. Support for Android 2.2 and up.

##Demo
![](https://github.com/H07000223/FlycoPageIndicator/blob/master/preview_FlycoPageIndicator.gif)

####[Here is a DemoApk download](http://fir.im/7qzm)

##Gradle

```groovy
dependencies{
    compile 'com.android.support:support-v4:22.2.1'
    compile 'com.nineoldandroids:library:2.4.0'
     compile 'com.flyco.pageindicator:FlycoPageIndicator_Lib:1.0.0@aar'
}
```

###FlycoPageIndicaor Attributes

|name|format|description|
|:---:|:---:|:---:|
| fpi_width | dimension | indicator width, unit dp,default 6dp
| fpi_height | dimension | indicator height,unit dp,default 6dp
| fpi_gap | dimension | indicator space between two indicators,unit dp,default 6dp
| fpi_strokeWidth | dimension | width of the stroke used to draw the indicators,default 0dp
| fpi_strokeColor | color | color of the stroke used to draw the indicators,default "#ffffff"
| fpi_isSnap | boolean | Whether or not the selected indicator snaps to the indicators,default false
| fpi_selectColor | color | indicator select color ,default "#ffffff"
| fpi_unselectColor | color | indicator unselect color ,default "#88ffffff" 
| fpi_cornerRadius | dimension | indicator corner raduis ,unit dp,default 3dp
| fpi_selectRes | reference | indicator select drawable resource
| fpi_unselectRes | reference | indicator unselect drawable resource


##Thanks
*   [NineOldAndroids](https://github.com/JakeWharton/NineOldAndroids)
