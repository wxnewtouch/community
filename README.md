## 跟随码匠笔记作者一起练习的spring boot项目

## 第一次提交实现的功能
    实现了githu授权登录的功能。
    
## spring boot 整合mybatis 添加驼峰匹配
```$xslt
mybatis.configuration.map-underscore-to-camel-case=true
mybatis.configuration.mapUnderscoreToCamelCase=true
```


## 集成mybatis generator插件
    对于本节课程中集成的mbg插件，我这个项目中就不写了。因为表少，后面的视频
    我还没有看，如果不添加表的话，应该就不用添加这个插件了。
    
    这节课中主要出现了两个问题，第一个，就是对于分页的插件。匠哥找到一个pageHelper
    这个插件之后，是没有使用的，这个插件我以前接触过，挺方便的。
    匠哥直接找到了官网推荐的mybatis自带的插件，是在mybatis的配置文件中直接
    添加了rowbounds的插件，这个可以直接传入offset，limit这两个参数，可以完成分页。
    
    第二个问题就是在项目启动的过程中报错，是因为，在spring的配置文件中，自动映射
    包应该是mapper，而不是model。主要就是这两个问题。
    
    当然，在这节课中，最重要的是学会了通过官网查找文档，根据文档来完成自己的
    功能。提高自己的阅读能力。