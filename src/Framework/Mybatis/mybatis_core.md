# Mybatis核心层
## 1. Mybatis初始化
### 1.1 BaseBuider 
> BaseBuider是众多Builder的父类如: XMLConfigBuilder、XMLMapperBuilder、SqlSourceBuilder。
### 1.2 XMLConfigBuilder
> 解析mybatis-config.xml文件。
```
private void parseConfiguration(XNode root) {
    try {
        this.propertiesElement(root.evalNode("properties"));
        Properties settings = this.settingsAsProperties(root.evalNode("settings"));
        this.loadCustomVfs(settings);
        this.loadCustomLogImpl(settings);
        this.typeAliasesElement(root.evalNode("typeAliases"));
        this.pluginElement(root.evalNode("plugins"));
        this.objectFactoryElement(root.evalNode("objectFactory"));
        this.objectWrapperFactoryElement(root.evalNode("objectWrapperFactory"));
        this.reflectorFactoryElement(root.evalNode("reflectorFactory"));
        this.settingsElement(settings);
        this.environmentsElement(root.evalNode("environments"));
        this.databaseIdProviderElement(root.evalNode("databaseIdProvider"));
        this.typeHandlerElement(root.evalNode("typeHandlers"));
        this.mapperElement(root.evalNode("mappers"));
    } catch (Exception var3) {
        throw new BuilderException("Error parsing SQL Mapper Configuration. Cause: " + var3, var3);
    }
}
```
#### 1.2.1 解析properties节点
> properties节点比较简单，就是值解析成Properties后设置到configuration中方便其它对象使用。
```
private void propertiesElement(XNode context) throws Exception {
    if (context != null) {
        Properties defaults = context.getChildrenAsProperties();
        String resource = context.getStringAttribute("resource");
        String url = context.getStringAttribute("url");
        if (resource != null && url != null) {
            throw new BuilderException("The properties element cannot specify both a URL and a resource based property file reference.  Please specify one or the other.");
        }

        if (resource != null) {
            defaults.putAll(Resources.getResourceAsProperties(resource));
        } else if (url != null) {
            defaults.putAll(Resources.getUrlAsProperties(url));
        }

        Properties vars = this.configuration.getVariables();
        if (vars != null) {
            defaults.putAll(vars);
        }

        this.parser.setVariables(defaults);
        this.configuration.setVariables(defaults);
    }

}
```
#### 1.2.2 解析settings节点
> settings节点是对mybatis如何运行进行配置的，所以只要将解析后的值设置到configuration中供其它对象使用即可。
```
private void settingsElement(Properties props) {
    this.configuration.setAutoMappingBehavior(AutoMappingBehavior.valueOf(props.getProperty("autoMappingBehavior", "PARTIAL")));
    this.configuration.setAutoMappingUnknownColumnBehavior(AutoMappingUnknownColumnBehavior.valueOf(props.getProperty("autoMappingUnknownColumnBehavior", "NONE")));
    this.configuration.setCacheEnabled(this.booleanValueOf(props.getProperty("cacheEnabled"), true));
    this.configuration.setProxyFactory((ProxyFactory)this.createInstance(props.getProperty("proxyFactory")));
    ......
}
```
#### 1.2.3 解析plugins节点
```

```
#### 1.2.4 解析objectFactory节点
```

```
#### 1.2.5 解析objectWrapperFactory节点
```

```
#### 1.2.6 解析reflectorFactory节点
```

```
#### 1.2.7 解析environments节点
> 区分不同环境的数据源与事物管理。
```
private void environmentsElement(XNode context) throws Exception {
        if (context != null) {
            if (this.environment == null) {
                this.environment = context.getStringAttribute("default");
            }

            Iterator var2 = context.getChildren().iterator();

            while(var2.hasNext()) {
                XNode child = (XNode)var2.next();
                String id = child.getStringAttribute("id");
                // 判断是否为同一环境
                if (this.isSpecifiedEnvironment(id)) {
                    TransactionFactory txFactory = this.transactionManagerElement(child.evalNode("transactionManager"));
                    DataSourceFactory dsFactory = this.dataSourceElement(child.evalNode("dataSource"));
                    DataSource dataSource = dsFactory.getDataSource();
                    Builder environmentBuilder = (new Builder(id)).transactionFactory(txFactory).dataSource(dataSource);
                    this.configuration.setEnvironment(environmentBuilder.build());
                }
            }
        }

    }
```
#### 1.2.8 解析typeAliases节点
> 将配置的别名注册到typeAliasRegistry中。
```
private void typeAliasesElement(XNode parent) {
    if (parent != null) {
        Iterator var2 = parent.getChildren().iterator();

        while(var2.hasNext()) {
            XNode child = (XNode)var2.next();
            String alias;
            if ("package".equals(child.getName())) {
                alias = child.getStringAttribute("name");
                // 扫描包下所有类，解析@Alias完成别名注册
                this.configuration.getTypeAliasRegistry().registerAliases(alias);
            } else {
                alias = child.getStringAttribute("alias");
                String type = child.getStringAttribute("type");

                try {
                    Class<?> clazz = Resources.classForName(type);
                    if (alias == null) {
                        this.typeAliasRegistry.registerAlias(clazz);
                    } else {
                        this.typeAliasRegistry.registerAlias(alias, clazz);
                    }
                } catch (ClassNotFoundException var7) {
                    throw new BuilderException("Error registering typeAlias for '" + alias + "'. Cause: " + var7, var7);
                }
            }
        }
    }

}

```
#### 1.2.9 解析databaseIdProvider节点
> 用于配置不同的数据库执行不同的sql,在加载Mapper配置文件sql时会根据databaseId进行匹配，为空或相同则匹配成功，当符合前两者条件时第二种的优先级会更高。
```
private void databaseIdProviderElement(XNode context) throws Exception {
    DatabaseIdProvider databaseIdProvider = null;
    if (context != null) {
        String type = context.getStringAttribute("type");
        if ("VENDOR".equals(type)) {
            type = "DB_VENDOR";
        }

        Properties properties = context.getChildrenAsProperties();
        databaseIdProvider = (DatabaseIdProvider)this.resolveClass(type).newInstance();
        databaseIdProvider.setProperties(properties);
    }

    Environment environment = this.configuration.getEnvironment();
    if (environment != null && databaseIdProvider != null) {
        String databaseId = databaseIdProvider.getDatabaseId(environment.getDataSource());
        this.configuration.setDatabaseId(databaseId);
    }

}
```
#### 1.2.10 解析typeHandlers节点
> 用于配置JavaType与JdbcType的相互转换
```
private void typeHandlerElement(XNode parent) {
    if (parent != null) {
        Iterator var2 = parent.getChildren().iterator();

        while(var2.hasNext()) {
            XNode child = (XNode)var2.next();
            String typeHandlerPackage;
            if ("package".equals(child.getName())) {
                typeHandlerPackage = child.getStringAttribute("name");
                this.typeHandlerRegistry.register(typeHandlerPackage);
            } else {
                typeHandlerPackage = child.getStringAttribute("javaType");
                String jdbcTypeName = child.getStringAttribute("jdbcType");
                String handlerTypeName = child.getStringAttribute("handler");
                Class<?> javaTypeClass = this.resolveClass(typeHandlerPackage);
                JdbcType jdbcType = this.resolveJdbcType(jdbcTypeName);
                Class<?> typeHandlerClass = this.resolveClass(handlerTypeName);
                if (javaTypeClass != null) {
                    if (jdbcType == null) {
                        this.typeHandlerRegistry.register(javaTypeClass, typeHandlerClass);
                    } else {
                        this.typeHandlerRegistry.register(javaTypeClass, jdbcType, typeHandlerClass);
                    }
                } else {
                    this.typeHandlerRegistry.register(typeHandlerClass);
                }
            }
        }
    }

}
```
### 1.3 XMLMapperBuilder
> 解析Mapper.xml文件
```
public void parse() {
    if (!this.configuration.isResourceLoaded(this.resource)) {
        this.configurationElement(this.parser.evalNode("/mapper"));
        this.configuration.addLoadedResource(this.resource);
        this.bindMapperForNamespace();
    }
    // mapper节点解析完毕后再次初始化那些引用了未初始化对象的节点
    this.parsePendingResultMaps();
    this.parsePendingCacheRefs();
    this.parsePendingStatements();
}
```
```
private void configurationElement(XNode context) {
    try {
        String namespace = context.getStringAttribute("namespace");
        if (namespace != null && !namespace.equals("")) {
            this.builderAssistant.setCurrentNamespace(namespace);
            this.cacheRefElement(context.evalNode("cache-ref"));
            this.cacheElement(context.evalNode("cache"));
            this.parameterMapElement(context.evalNodes("/mapper/parameterMap"));
            this.resultMapElements(context.evalNodes("/mapper/resultMap"));
            this.sqlElement(context.evalNodes("/mapper/sql"));
            this.buildStatementFromContext(context.evalNodes("select|insert|update|delete"));
        } else {
            throw new BuilderException("Mapper's namespace cannot be empty");
        }
    } catch (Exception var3) {
        throw new BuilderException("Error parsing Mapper XML. The XML location is '" + this.resource + "'. Cause: " + var3, var3);
    }
}
```
#### 1.3.1 解析cache节点
> 将创建好的Cache保存到Configruation.caches中，并记录了每个Mapper与其Cahce对象的关系
```
private void cacheElement(XNode context) throws Exception {
    if (context != null) {
        String type = context.getStringAttribute("type", "PERPETUAL");
        Class<? extends Cache> typeClass = this.typeAliasRegistry.resolveAlias(type);
        String eviction = context.getStringAttribute("eviction", "LRU");
        Class<? extends Cache> evictionClass = this.typeAliasRegistry.resolveAlias(eviction);
        Long flushInterval = context.getLongAttribute("flushInterval");
        Integer size = context.getIntAttribute("size");
        boolean readWrite = !context.getBooleanAttribute("readOnly", false);
        boolean blocking = context.getBooleanAttribute("blocking", false);
        Properties props = context.getChildrenAsProperties();
        // 创建cache
        this.builderAssistant.useNewCache(typeClass, evictionClass, flushInterval, size, readWrite, blocking, props);
    }
}
```
> 配置CacheBuilder，最后build()是创建cache的核心方法
```
public Cache useNewCache(Class<? extends Cache> typeClass, Class<? extends Cache> evictionClass, Long flushInterval, Integer size, boolean readWrite, boolean blocking, Properties props) {
    Cache cache = (new CacheBuilder(this.currentNamespace)).implementation((Class)this.valueOrDefault(typeClass, PerpetualCache.class)).addDecorator((Class)this.valueOrDefault(evictionClass, LruCache.class)).clearInterval(flushInterval).size(size).readWrite(readWrite).blocking(blocking).properties(props).build();
    this.configuration.addCache(cache);
    this.currentCache = cache;
    return cache;
}
```
```
public Cache build() {
    // 设置默认的Cache实现，Mybatis的Cache只有一个实现PerpetualCache但有多个Cache装饰器
    this.setDefaultImplementations();
    Cache cache = this.newBaseCacheInstance(this.implementation, this.id);
    this.setCacheProperties((Cache)cache);
    if (PerpetualCache.class.equals(cache.getClass())) {
        Iterator var2 = this.decorators.iterator();

        while(var2.hasNext()) {
            Class<? extends Cache> decorator = (Class)var2.next();
            // 应用Cache装饰器
            cache = this.newCacheDecoratorInstance(decorator, (Cache)cache);
            // 设置配置的属性值
            this.setCacheProperties((Cache)cache);
        }
        // 根据配置的属性应用标准装饰器如：ScheduledCache、SerializedCache
        cache = this.setStandardDecorators((Cache)cache);
    } else if (!LoggingCache.class.isAssignableFrom(cache.getClass())) {
        cache = new LoggingCache((Cache)cache);
    }

    return (Cache)cache;
}
```
#### 1.3.2 解析cache-ref节点
> 建立cache-ref所在的namespace与配置的namespace的cache的关系
```
private void cacheRefElement(XNode context) {
    if (context != null) {
        // 记录了cache-ref所在的namespace与cache-ref设置namespace属性值的关系
        this.configuration.addCacheRef(this.builderAssistant.getCurrentNamespace(), context.getStringAttribute("namespace"));
        CacheRefResolver cacheRefResolver = new CacheRefResolver(this.builderAssistant, context.getStringAttribute("namespace"));

        try {
            // 将引用关系设置到当前的namespace cache中
            cacheRefResolver.resolveCacheRef();
        } catch (IncompleteElementException var4) {
            // 如引用了未初始化的cache则在最后会再次初始化
            this.configuration.addIncompleteCacheRef(cacheRefResolver);
        }
    }

}
```
#### 1.3.3 解析resultMap节点
> 
```
private ResultMap resultMapElement(XNode resultMapNode, List<ResultMapping> additionalResultMappings, Class<?> enclosingType) throws Exception {
    ErrorContext.instance().activity("processing " + resultMapNode.getValueBasedIdentifier());
    String type = resultMapNode.getStringAttribute("type", resultMapNode.getStringAttribute("ofType", resultMapNode.getStringAttribute("resultType", resultMapNode.getStringAttribute("javaType"))));
    Class<?> typeClass = this.resolveClass(type);
    if (typeClass == null) {
        typeClass = this.inheritEnclosingType(resultMapNode, enclosingType);
    }

    Discriminator discriminator = null;
    List<ResultMapping> resultMappings = new ArrayList();
    resultMappings.addAll(additionalResultMappings);
    List<XNode> resultChildren = resultMapNode.getChildren();
    Iterator var9 = resultChildren.iterator();

    while(var9.hasNext()) {
        XNode resultChild = (XNode)var9.next();
        if ("constructor".equals(resultChild.getName())) {
            this.processConstructorElement(resultChild, typeClass, resultMappings);
        } else if ("discriminator".equals(resultChild.getName())) {
            discriminator = this.processDiscriminatorElement(resultChild, typeClass, resultMappings);
        } else {
            List<ResultFlag> flags = new ArrayList();
            if ("id".equals(resultChild.getName())) {
                flags.add(ResultFlag.ID);
            }

            resultMappings.add(this.buildResultMappingFromContext(resultChild, typeClass, flags));
        }
    }

    String id = resultMapNode.getStringAttribute("id", resultMapNode.getValueBasedIdentifier());
    String extend = resultMapNode.getStringAttribute("extends");
    Boolean autoMapping = resultMapNode.getBooleanAttribute("autoMapping");
    ResultMapResolver resultMapResolver = new ResultMapResolver(this.builderAssistant, id, typeClass, extend, discriminator, resultMappings, autoMapping);

    try {
        return resultMapResolver.resolve();
    } catch (IncompleteElementException var14) {
        this.configuration.addIncompleteResultMap(resultMapResolver);
        throw var14;
    }
}
```
### 1.4 解析Sql节点
#### 1.4.1 MapperStatment
> 用于表示XML中配置的Sql节点
### 1.5 绑定Mapper 




