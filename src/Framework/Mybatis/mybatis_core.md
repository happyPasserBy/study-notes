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
> 创建resultMap添加到Configuration的resultMaps中
```
private ResultMap resultMapElement(XNode resultMapNode, List<ResultMapping> additionalResultMappings, Class<?> enclosingType) throws Exception {
    ErrorContext.instance().activity("processing " + resultMapNode.getValueBasedIdentifier());
    // 获取resultMap返回值类型
    String type = resultMapNode.getStringAttribute("type", resultMapNode.getStringAttribute("ofType", resultMapNode.getStringAttribute("resultType", resultMapNode.getStringAttribute("javaType"))));
    Class<?> typeClass = this.resolveClass(type);
    if (typeClass == null) {
        typeClass = this.inheritEnclosingType(resultMapNode, enclosingType);
    }

    Discriminator discriminator = null;
    // 记录解析结果
    List<ResultMapping> resultMappings = new ArrayList();
    resultMappings.addAll(additionalResultMappings);
    // 解析子节点
    List<XNode> resultChildren = resultMapNode.getChildren();
    Iterator var9 = resultChildren.iterator();

    while(var9.hasNext()) {
        XNode resultChild = (XNode)var9.next();
        // constructor
        if ("constructor".equals(resultChild.getName())) {
            this.processConstructorElement(resultChild, typeClass, resultMappings);
        } else if ("discriminator".equals(resultChild.getName())) {
        // discriminator
            discriminator = this.processDiscriminatorElement(resultChild, typeClass, resultMappings);
        } else {
        // id
            List<ResultFlag> flags = new ArrayList();
            if ("id".equals(resultChild.getName())) {
                flags.add(ResultFlag.ID);
            }

            resultMappings.add(this.buildResultMappingFromContext(resultChild, typeClass, flags));
        }
    }

    String id = resultMapNode.getStringAttribute("id", resultMapNode.getValueBasedIdentifier());
    // 获取继承的resultMap
    String extend = resultMapNode.getStringAttribute("extends");
    // 是否开启自动映射
    Boolean autoMapping = resultMapNode.getBooleanAttribute("autoMapping");
    ResultMapResolver resultMapResolver = new ResultMapResolver(this.builderAssistant, id, typeClass, extend, discriminator, resultMappings, autoMapping);

    try {
        // 添加到 Configuration 的resultMaps中，以resultMap的 namespace.id 为key resultMap为value
        return resultMapResolver.resolve();
    } catch (IncompleteElementException var14) {
        this.configuration.addIncompleteResultMap(resultMapResolver);
        throw var14;
    }
}
```
#### 1.3.3.1 buildResultMappingFromContext
> 创建resultMap的ResultMapping
```
private ResultMapping buildResultMappingFromContext(XNode context, Class<?> resultType, List<ResultFlag> flags) throws Exception {
    String property;
    if (flags.contains(ResultFlag.CONSTRUCTOR)) {
        property = context.getStringAttribute("name");
    } else {
        property = context.getStringAttribute("property");
    }

    String column = context.getStringAttribute("column");
    String javaType = context.getStringAttribute("javaType");
    String jdbcType = context.getStringAttribute("jdbcType");
    String nestedSelect = context.getStringAttribute("select");
    String nestedResultMap = context.getStringAttribute("resultMap", this.processNestedResultMappings(context, Collections.emptyList(), resultType));
    String notNullColumn = context.getStringAttribute("notNullColumn");
    String columnPrefix = context.getStringAttribute("columnPrefix");
    String typeHandler = context.getStringAttribute("typeHandler");
    String resultSet = context.getStringAttribute("resultSet");
    String foreignColumn = context.getStringAttribute("foreignColumn");
    boolean lazy = "lazy".equals(context.getStringAttribute("fetchType", this.configuration.isLazyLoadingEnabled() ? "lazy" : "eager"));
    // 根据javaType、typeHandler、jdbcType到别名系统中找到相应的class
    Class<?> javaTypeClass = this.resolveClass(javaType);
    Class<? extends TypeHandler<?>> typeHandlerClass = this.resolveClass(typeHandler);
    JdbcType jdbcTypeEnum = this.resolveJdbcType(jdbcType);
    // 开始创建
    return this.builderAssistant.buildResultMapping(resultType, property, column, javaTypeClass, jdbcTypeEnum, nestedSelect, nestedResultMap, notNullColumn, columnPrefix, typeHandlerClass, flags, resultSet, foreignColumn, lazy);
}
```
### 1.3.3.2 public ResultMap resolve()
> 创建resultMap并添加
```
public ResultMap resolve() {
    return this.assistant.addResultMap(this.id, this.type, this.extend, this.discriminator, this.resultMappings, this.autoMapping);
}
```
```
public ResultMap addResultMap(String id, Class<?> type, String extend, Discriminator discriminator, List<ResultMapping> resultMappings, Boolean autoMapping) {
    id = this.applyCurrentNamespace(id, false);
    extend = this.applyCurrentNamespace(extend, true);
    ResultMap resultMap;
    // 有继承的情况
    if (extend != null) {
        if (!this.configuration.hasResultMap(extend)) {
            throw new IncompleteElementException("Could not find a parent resultmap with id '" + extend + "'");
        }

        resultMap = this.configuration.getResultMap(extend);
        List<ResultMapping> extendedResultMappings = new ArrayList(resultMap.getResultMappings());
        // 删除已经覆盖的ResultMapping
        extendedResultMappings.removeAll(resultMappings);
        boolean declaresConstructor = false;
        Iterator var10 = resultMappings.iterator();
        // 删除已经覆盖的CONSTRUCTOR节点
        while(var10.hasNext()) {
            ResultMapping resultMapping = (ResultMapping)var10.next();
            if (resultMapping.getFlags().contains(ResultFlag.CONSTRUCTOR)) {
                declaresConstructor = true;
                break;
            }
        }

        if (declaresConstructor) {
            extendedResultMappings.removeIf((resultMappingx) -> {
                return resultMappingx.getFlags().contains(ResultFlag.CONSTRUCTOR);
            });
        }

        resultMappings.addAll(extendedResultMappings);
    }
    // 最终的创建
    resultMap = (new org.apache.ibatis.mapping.ResultMap.Builder(this.configuration, id, type, resultMappings, autoMapping)).discriminator(discriminator).build();
    // 添加到configuration
    this.configuration.addResultMap(resultMap);
    return resultMap;
}
```
### 1.4 解析Sql节点
#### 1.4.1 MappedStatement
> 用于表示XML中配置的Sql节点
```
public void parseStatementNode() {
    String id = this.context.getStringAttribute("id");
    // 判断当前的databaseId与配置的databaseIdProvider是否相匹配
    String databaseId = this.context.getStringAttribute("databaseId");
    if (this.databaseIdMatchesCurrent(id, databaseId, this.requiredDatabaseId)) {
        String nodeName = this.context.getNode().getNodeName();
        // slq类型，select、insert、update、delete
        SqlCommandType sqlCommandType = SqlCommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));
        boolean isSelect = sqlCommandType == SqlCommandType.SELECT;
        boolean flushCache = this.context.getBooleanAttribute("flushCache", !isSelect);
        boolean useCache = this.context.getBooleanAttribute("useCache", isSelect);
        boolean resultOrdered = this.context.getBooleanAttribute("resultOrdered", false);
        // 处理include节点
        XMLIncludeTransformer includeParser = new XMLIncludeTransformer(this.configuration, this.builderAssistant);
        includeParser.applyIncludes(this.context.getNode());
        String parameterType = this.context.getStringAttribute("parameterType");
        Class<?> parameterTypeClass = this.resolveClass(parameterType);
        String lang = this.context.getStringAttribute("lang");
        LanguageDriver langDriver = this.getLanguageDriver(lang);
        // 处理selectKey
        this.processSelectKeyNodes(id, parameterTypeClass, langDriver);
        String keyStatementId = id + "!selectKey";
        keyStatementId = this.builderAssistant.applyCurrentNamespace(keyStatementId, true);
        Object keyGenerator;
        if (this.configuration.hasKeyGenerator(keyStatementId)) {
            keyGenerator = this.configuration.getKeyGenerator(keyStatementId);
        } else {
            keyGenerator = this.context.getBooleanAttribute("useGeneratedKeys", this.configuration.isUseGeneratedKeys() && SqlCommandType.INSERT.equals(sqlCommandType)) ? Jdbc3KeyGenerator.INSTANCE : NoKeyGenerator.INSTANCE;
        }

        SqlSource sqlSource = langDriver.createSqlSource(this.configuration, this.context, parameterTypeClass);
        StatementType statementType = StatementType.valueOf(this.context.getStringAttribute("statementType", StatementType.PREPARED.toString()));
        Integer fetchSize = this.context.getIntAttribute("fetchSize");
        Integer timeout = this.context.getIntAttribute("timeout");
        String parameterMap = this.context.getStringAttribute("parameterMap");
        String resultType = this.context.getStringAttribute("resultType");
        Class<?> resultTypeClass = this.resolveClass(resultType);
        String resultMap = this.context.getStringAttribute("resultMap");
        String resultSetType = this.context.getStringAttribute("resultSetType");
        ResultSetType resultSetTypeEnum = this.resolveResultSetType(resultSetType);
        if (resultSetTypeEnum == null) {
            resultSetTypeEnum = this.configuration.getDefaultResultSetType();
        }

        String keyProperty = this.context.getStringAttribute("keyProperty");
        String keyColumn = this.context.getStringAttribute("keyColumn");
        String resultSets = this.context.getStringAttribute("resultSets");
        this.builderAssistant.addMappedStatement(id, sqlSource, statementType, sqlCommandType, fetchSize, timeout, parameterMap, parameterTypeClass, resultMap, resultTypeClass, resultSetTypeEnum, flushCache, useCache, resultOrdered, (KeyGenerator)keyGenerator, keyProperty, keyColumn, databaseId, langDriver, resultSets);
    }
}
```
#### 1.4.1.1 解析applyIncludes
```
public void applyIncludes(Node source) {
    // 获取全局变量
    Properties variablesContext = new Properties();
    Properties configurationVariables = this.configuration.getVariables();
    Optional var10000 = Optional.ofNullable(configurationVariables);
    Objects.requireNonNull(variablesContext);
    var10000.ifPresent(variablesContext::putAll);
    this.applyIncludes(source, variablesContext, false);
}
```
#### 1.4.1.2 解析applyIncludes
```
private void applyIncludes(Node source, Properties variablesContext, boolean included) {
    if (source.getNodeName().equals("include")) {
        // 获取refid所引用的sql节点
        Node toInclude = this.findSqlFragment(this.getStringAttribute(source, "refid"), variablesContext);
        // 将source中配置的Properties与全局配置的Properties合并
        Properties toIncludeContext = this.getVariablesContext(source, variablesContext);
        // 递归处理防止循环嵌套include
        this.applyIncludes(toInclude, toIncludeContext, true);
        if (toInclude.getOwnerDocument() != source.getOwnerDocument()) {
            toInclude = source.getOwnerDocument().importNode(toInclude, true);
        }
        // 将include节点替换成sql节点
        source.getParentNode().replaceChild(toInclude, source);
        
        while(toInclude.hasChildNodes()) {
            toInclude.getParentNode().insertBefore(toInclude.getFirstChild(), toInclude);
        }
        // 删除include节点
        toInclude.getParentNode().removeChild(toInclude);
    } else if (source.getNodeType() == 1) {
        int i;
        if (included && !variablesContext.isEmpty()) {
            NamedNodeMap attributes = source.getAttributes();

            for(i = 0; i < attributes.getLength(); ++i) {
                Node attr = attributes.item(i);
                attr.setNodeValue(PropertyParser.parse(attr.getNodeValue(), variablesContext));
            }
        }

        NodeList children = source.getChildNodes();

        for(i = 0; i < children.getLength(); ++i) {
            this.applyIncludes(children.item(i), variablesContext, included);
        }
    } else if (included && source.getNodeType() == 3 && !variablesContext.isEmpty()) {
        // 替换${}占位符
        source.setNodeValue(PropertyParser.parse(source.getNodeValue(), variablesContext));
    }

}
```
#### 1.4.2 解析selectKey节点
```

```
### 1.5 绑定Mapper 

### 1.6 解析incomplete集合
```
public void parse() {
    if (!this.configuration.isResourceLoaded(this.resource)) {
        this.configurationElement(this.parser.evalNode("/mapper"));
        this.configuration.addLoadedResource(this.resource);
        this.bindMapperForNamespace();
    }
    
    this.parsePendingResultMaps();
    this.parsePendingCacheRefs();
    this.parsePendingStatements();
}
```
```
private void parsePendingResultMaps() {
    Collection<ResultMapResolver> incompleteResultMaps = this.configuration.getIncompleteResultMaps();
    // 加锁
    synchronized(incompleteResultMaps) {
        Iterator iter = incompleteResultMaps.iterator();

        while(iter.hasNext()) {
            try {
                // 重新解析
                ((ResultMapResolver)iter.next()).resolve();
                // 删除
                iter.remove();
            } catch (IncompleteElementException var6) {
            }
        }

    }
}
```



