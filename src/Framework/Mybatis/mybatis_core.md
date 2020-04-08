# Mybatis核心层
## 1. Mybatis初始化
### 1.1 BaseBuider 
> BaseBuider是众多Builder的父类如: XMLConfigBuilder、XMLMapperBuilder、SqlSourceBuilder。
### 1.2 XMLConfigBuilder
> 解析mybatis-config.xml文件。
### 1.3 XMLMapperBuilder
> 解析Mapper.xml文件
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
        this.builderAssistant.useNewCache(typeClass, evictionClass, flushInterval, size, readWrite, blocking, props);
    }
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
            cacheRefResolver.resolveCacheRef();
        } catch (IncompleteElementException var4) {
            this.configuration.addIncompleteCacheRef(cacheRefResolver);
        }
    }

}
```
#### 1.3.3 解析resultMap节点
> 
### 1.4 解析Sql节点
#### 1.4.1 MapperStatment
> 用于表示XML中配置的Sql节点
### 1.5 绑定Mapper 




