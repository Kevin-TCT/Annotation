# Annotation
#### 注解分类：
<b>1、运行期注解（RunTime）：</b><br/>
&emsp;&emsp;在工程运行时，通过反射技术去获取信息，反射存在一定的性能问题，比较损耗性能。<br/><br/>
<b>2、编译期注解（Compile time）：</b><br/>
&emsp;&emsp;编译时指定一个Annotation处理器，该处理器实现了Processor接口，通过该接口的方法来检查获取类中的注解，并生成新的代码文件。
运行时，通过当前的文件名拼接上自定义规则的后缀，查找编译时生成的类，然后进行处理。<br/>
<br/>
&emsp;&emsp;整个流程大概就是: <br/>
&emsp;&emsp;&emsp;&emsp;1、创建注解<br/>
&emsp;&emsp;&emsp;&emsp;2、创建注解处理器<br/>
&emsp;&emsp;&emsp;&emsp;3、代码中使用注解<br/>
&emsp;&emsp;&emsp;&emsp;4、编译时注解处理插件会使用自定义的注解处理器去处理注解，生成相应的代码<br/>
<br/>
<br/>

<b><font color=red>注解的作用：</font></b><br/>
&emsp;&emsp;1、生成文档： @Documented 通过给Annotation注解加上@Documented标签，能使该Annotation标签出现在javadoc中.<br/>
&emsp;&emsp;2、编译时进行格式检查：@Override @Deprecated @SuppressWarnings<br/>
&emsp;&emsp;3、在反射中使用Annotation<br/>
&emsp;&emsp;4、注解处理器: 自动完成日常编码中需要重复编写的部分<br/>
&emsp;&emsp;5、跟踪代码依赖性，实现替代配置文件功能。<br/>

<br/>
<br/>
<br/>

[gradle之apt与annotationProcessor与kapt](https://blog.csdn.net/sinat_31057219/article/details/82798660)<br/>
&emsp;&emsp;<font color=red>APT(Annotation Processing Tool)</font>是一种处理注释的工具,它对源代码文件进行检测找出其中的Annotation，根据注解自动生成代码。
Annotation处理器在处理Annotation时可以根据源文件中的Annotation生成额外的源文件和其它的文件(文件具体内容由Annotation处理器的编写者决定),
APT还会编译生成的源文件和原来的源文件，将它们一起生成class文件。<br/>

[javac和jack编译](https://www.jianshu.com/p/4877022d2344)<br/>
[如何看待 Google 废弃 Jack 而直接在 javac 和 dx 上支持 Java 8?](https://www.zhihu.com/question/57149643)<br/>
<br/>
<br/>


[JakeWharton的Butterknife](https://github.com/JakeWharton/butterknife) <br/>
[Squre公司研发的javapoet，用于方便生成Java代码](https://github.com/square/javapoet)<br/>
<br/>
[比较全的apt介绍](https://github.com/yangchong211/YCApt)<br/>
[Android编译时注解](https://www.jianshu.com/p/3052fa51ee95)<br/>
[编译时注解](https://blog.csdn.net/a153614131/article/details/53248571)<br/>

<br/>
 
&copy; KevinTu 