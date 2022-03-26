# JAVA知识点速记

## spring ioc速记

### spring生命周期

1. 实例化bean实例
2. 设置属性值
3. 初始化阶段

   1. aware接口

   2. BeanPostProcessor的前置处理

   3. initializingBean接口的afterPropertySet方法

   4. Init-method 方法调用

   5. BeanPostProcessor的后置处理
4. 注册destruntion回调接口
5. 运行使用
6. 是否实现DisposableBean接口，执行destroy方法
7. 是否配置自定义destory方法
### spring的AbstractApplicationContext的refresh()方法都做了什么呢？

<img src="/Users/liyun/Documents/截屏2022-02-27 15.29.37.png" style="zoom: 50%;" />

以注解AnnotationConfigApplicationContext为例

```java
public AnnotationConfigApplicationContext(String... basePackages) {
		this();
		scan(basePackages);
		refresh();
}
```
调用AbstracApplicationContext的refresh()方法，模板方法，有很多子类实现的地方


  ```java
@Override
	public void refresh() throws BeansException, IllegalStateException {
		synchronized (this.startupShutdownMonitor) {
			// Prepare this context for refreshing.
			prepareRefresh();

			// Tell the subclass to refresh the internal bean factory.
			ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

			// Prepare the bean factory for use in this context.
			prepareBeanFactory(beanFactory);

			try {
				// Allows post-processing of the bean factory in context subclasses.
				postProcessBeanFactory(beanFactory);

				// Invoke factory processors registered as beans in the context.
				invokeBeanFactoryPostProcessors(beanFactory);

				// Register bean processors that intercept bean creation.
				registerBeanPostProcessors(beanFactory);

				// Initialize message source for this context.
				initMessageSource();

				// Initialize event multicaster for this context.
				initApplicationEventMulticaster();

				// Initialize other special beans in specific context subclasses.
				onRefresh();

				// Check for listener beans and register them.
				registerListeners();

				// Instantiate all remaining (non-lazy-init) singletons.
				finishBeanFactoryInitialization(beanFactory);

				// Last step: publish corresponding event.
				finishRefresh();
			}

			catch (BeansException ex) {
				if (logger.isWarnEnabled()) {
					logger.warn("Exception encountered during context initialization - " +
							"cancelling refresh attempt: " + ex);
				}

				// Destroy already created singletons to avoid dangling resources.
				destroyBeans();

				// Reset 'active' flag.
				cancelRefresh(ex);

				// Propagate exception to caller.
				throw ex;
			}
			
  ```
首先会加载beanDefination到容器中
这个地方后续再总结，

现在只看生成spring中bean的代码
在finishBeanFactoryInitialization(beanFactory);方法会实例化懒加载的单例
从beanDefinitionNames获得所有要加载到容器中实例的名字beanNames
依次循环beanNames
如果不是工厂类，直接就getBean();
执行真正生成bean的方法doGetBean

```java
      
    // Instantiate all remaining (non-lazy-init) singletons.
		beanFactory.preInstantiateSingletons();
      
    具体看
    @Override
	public void preInstantiateSingletons() throws BeansException {
		if (logger.isTraceEnabled()) {
			logger.trace("Pre-instantiating singletons in " + this);
		}

		// Iterate over a copy to allow for init methods which in turn register new bean definitions.
		// While this may not be part of the regular factory bootstrap, it does otherwise work fine.
		List<String> beanNames = new ArrayList<>(this.beanDefinitionNames);

		// Trigger initialization of all non-lazy singleton beans...
		for (String beanName : beanNames) {
			RootBeanDefinition bd = getMergedLocalBeanDefinition(beanName);
			if (!bd.isAbstract() && bd.isSingleton() && !bd.isLazyInit()) {
				if (isFactoryBean(beanName)) {
					Object bean = getBean(FACTORY_BEAN_PREFIX + beanName);
					if (bean instanceof FactoryBean) {
						final FactoryBean<?> factory = (FactoryBean<?>) bean;
						boolean isEagerInit;
						if (System.getSecurityManager() != null && factory instanceof SmartFactoryBean) {
							isEagerInit = AccessController.doPrivileged((PrivilegedAction<Boolean>)
											((SmartFactoryBean<?>) factory)::isEagerInit,
									getAccessControlContext());
						}
						else {
							isEagerInit = (factory instanceof SmartFactoryBean &&
									((SmartFactoryBean<?>) factory).isEagerInit());
						}
						if (isEagerInit) {
							getBean(beanName);
						}
					}
				}
				else {
					getBean(beanName);
				}
			}
		}
     
```
doGetBean方法中

会首先从三级缓存中获取，三级缓存中没有，就从父容器找，父容器没有，就继续
首先生成依赖的类的实例放入容器中

最后执行public Object getSingleton(String beanName, ObjectFactory<?> singletonFactory)方法


```java    
      @SuppressWarnings("unchecked")
    	protected <T> T doGetBean(final String name, @Nullable final Class<T> requiredType,
    			@Nullable final Object[] args, boolean typeCheckOnly) throws BeansException {
    
    		final String beanName = transformedBeanName(name);
    		Object bean;
    
    		// Eagerly check singleton cache for manually registered singletons.
    		Object sharedInstance = getSingleton(beanName);
    		if (sharedInstance != null && args == null) {
    			if (logger.isTraceEnabled()) {
    				if (isSingletonCurrentlyInCreation(beanName)) {
    					logger.trace("Returning eagerly cached instance of singleton bean '" + beanName +
    							"' that is not fully initialized yet - a consequence of a circular reference");
    				}
    				else {
    					logger.trace("Returning cached instance of singleton bean '" + beanName + "'");
    				}
    			}
    			bean = getObjectForBeanInstance(sharedInstance, name, beanName, null);
    		}
    
    		else {
    			// Fail if we're already creating this bean instance:
    			// We're assumably within a circular reference.
    			if (isPrototypeCurrentlyInCreation(beanName)) {
    				throw new BeanCurrentlyInCreationException(beanName);
    			}
    
    			// Check if bean definition exists in this factory.
    			BeanFactory parentBeanFactory = getParentBeanFactory();
    			if (parentBeanFactory != null && !containsBeanDefinition(beanName)) {
    				// Not found -> check parent.
    				String nameToLookup = originalBeanName(name);
    				if (parentBeanFactory instanceof AbstractBeanFactory) {
    					return ((AbstractBeanFactory) parentBeanFactory).doGetBean(
    							nameToLookup, requiredType, args, typeCheckOnly);
    				}
    				else if (args != null) {
    					// Delegation to parent with explicit args.
    					return (T) parentBeanFactory.getBean(nameToLookup, args);
    				}
    				else if (requiredType != null) {
    					// No args -> delegate to standard getBean method.
    					return parentBeanFactory.getBean(nameToLookup, requiredType);
    				}
    				else {
    					return (T) parentBeanFactory.getBean(nameToLookup);
    				}
    			}
    
    			if (!typeCheckOnly) {
    				markBeanAsCreated(beanName);
    			}
    
    			try {
    				final RootBeanDefinition mbd = getMergedLocalBeanDefinition(beanName);
    				checkMergedBeanDefinition(mbd, beanName, args);
    
    				// Guarantee initialization of beans that the current bean depends on.
    				String[] dependsOn = mbd.getDependsOn();
    				if (dependsOn != null) {
    					for (String dep : dependsOn) {
    						if (isDependent(beanName, dep)) {
    							throw new BeanCreationException(mbd.getResourceDescription(), beanName,
    									"Circular depends-on relationship between '" + beanName + "' and '" + dep + "'");
    						}
    						registerDependentBean(dep, beanName);
    						try {
    							getBean(dep);
    						}
    						catch (NoSuchBeanDefinitionException ex) {
    							throw new BeanCreationException(mbd.getResourceDescription(), beanName,
    									"'" + beanName + "' depends on missing bean '" + dep + "'", ex);
    						}
    					}
    				}
    
    				// Create bean instance.
    				if (mbd.isSingleton()) {
    					sharedInstance = getSingleton(beanName,
    							() -> {
    								try {
    									return AbstractBeanFactory.this.createBean(beanName, mbd, args);
    								} catch (BeansException ex) {
    									// Explicitly remove instance from singleton cache: It might have been put there
    									// eagerly by the creation process, to allow for circular reference resolution.
    									// Also remove any beans that received a temporary reference to the bean.
    									AbstractBeanFactory.this.destroySingleton(beanName);
    									throw ex;
    								}
    							});
    					bean = getObjectForBeanInstance(sharedInstance, name, beanName, mbd);
    				}
    
    				else if (mbd.isPrototype()) {
    					// It's a prototype -> create a new instance.
    					Object prototypeInstance = null;
    					try {
    						beforePrototypeCreation(beanName);
    						prototypeInstance = createBean(beanName, mbd, args);
    					}
    					finally {
    						afterPrototypeCreation(beanName);
    					}
    					bean = getObjectForBeanInstance(prototypeInstance, name, beanName, mbd);
    				}
    
    				else {
    					String scopeName = mbd.getScope();
    					final Scope scope = this.scopes.get(scopeName);
    					if (scope == null) {
    						throw new IllegalStateException("No Scope registered for scope name '" + scopeName + "'");
    					}
    					try {
    						Object scopedInstance = scope.get(beanName, () -> {
    							beforePrototypeCreation(beanName);
    							try {
    								return createBean(beanName, mbd, args);
    							}
    							finally {
    								afterPrototypeCreation(beanName);
    							}
    						});
    						bean = getObjectForBeanInstance(scopedInstance, name, beanName, mbd);
    					}
    					catch (IllegalStateException ex) {
    						throw new BeanCreationException(beanName,
    								"Scope '" + scopeName + "' is not active for the current thread; consider " +
    								"defining a scoped proxy for this bean if you intend to refer to it from a singleton",
    								ex);
    					}
    				}
    			}
    			catch (BeansException ex) {
    				cleanupAfterBeanCreationFailure(beanName);
    				throw ex;
    			}
    		}
    
    		// Check if required type matches the type of the actual bean instance.
    		if (requiredType != null && !requiredType.isInstance(bean)) {
    			try {
    				T convertedBean = getTypeConverter().convertIfNecessary(bean, requiredType);
    				if (convertedBean == null) {
    					throw new BeanNotOfRequiredTypeException(name, requiredType, bean.getClass());
    				}
    				return convertedBean;
    			}
    			catch (TypeMismatchException ex) {
    				if (logger.isTraceEnabled()) {
    					logger.trace("Failed to convert bean '" + name + "' to required type '" +
    							ClassUtils.getQualifiedName(requiredType) + "'", ex);
    				}
    				throw new BeanNotOfRequiredTypeException(name, requiredType, bean.getClass());
    			}
    		}
    		return (T) bean;
    	}
```

这个时候的getSingleton方法，已经不是最开始的从三级缓存里寻找singleton实例的方法了，这次是真的生成方法，
在一级缓存singletonObjects中没找到bea实例，就开始进入同步方法，实际生成
回调方法实际生成，singletonObject = singletonFactory.getObject();
回调方法中会调用AbstractBeanFactory的createBean()方法

```java
/**
	 * Return the (raw) singleton object registered under the given name,
	 * creating and registering a new one if none registered yet.
	 * @param beanName the name of the bean
	 * @param singletonFactory the ObjectFactory to lazily create the singleton
	 * with, if necessary
	 * @return the registered singleton object
	 */
	public Object getSingleton(String beanName, ObjectFactory<?> singletonFactory) {
		Assert.notNull(beanName, "Bean name must not be null");
		synchronized (this.singletonObjects) {
			Object singletonObject = this.singletonObjects.get(beanName);
			if (singletonObject == null) {
				if (this.singletonsCurrentlyInDestruction) {
					throw new BeanCreationNotAllowedException(beanName,
							"Singleton bean creation not allowed while singletons of this factory are in destruction " +
							"(Do not request a bean from a BeanFactory in a destroy method implementation!)");
				}
				if (logger.isDebugEnabled()) {
					logger.debug("Creating shared instance of singleton bean '" + beanName + "'");
				}
				beforeSingletonCreation(beanName);
				boolean newSingleton = false;
				boolean recordSuppressedExceptions = (this.suppressedExceptions == null);
				if (recordSuppressedExceptions) {
					this.suppressedExceptions = new LinkedHashSet<>();
				}
				try {
					singletonObject = singletonFactory.getObject();
					newSingleton = true;
				}
				catch (IllegalStateException ex) {
					// Has the singleton object implicitly appeared in the meantime ->
					// if yes, proceed with it since the exception indicates that state.
					singletonObject = this.singletonObjects.get(beanName);
					if (singletonObject == null) {
						throw ex;
					}
				}
				catch (BeanCreationException ex) {
					if (recordSuppressedExceptions) {
						for (Exception suppressedException : this.suppressedExceptions) {
							ex.addRelatedCause(suppressedException);
						}
					}
					throw ex;
				}
				finally {
					if (recordSuppressedExceptions) {
						this.suppressedExceptions = null;
					}
					afterSingletonCreation(beanName);
				}
				if (newSingleton) {
					addSingleton(beanName, singletonObject);
				}
			}
			return singletonObject;
		}
	}
```

getSingleton回调方法中会调用AbstractBeanFactory的createBean方法

```java     

          if (mbd.isSingleton()) {
    				sharedInstance = getSingleton(beanName,
    						() -> {
    							try {
    								return AbstractBeanFactory.this.createBean(beanName, mbd, args);
    							} catch (BeansException ex) {
    								// Explicitly remove instance from singleton cache: It might have been put there
    								// eagerly by the creation process, to allow for circular reference resolution.
    								// Also remove any beans that received a temporary reference to the bean.
    								AbstractBeanFactory.this.destroySingleton(beanName);
    								throw ex;
    							}
    						});
    				bean = getObjectForBeanInstance(sharedInstance, name, beanName, mbd);
    			}   
```

AbstractBeanFactory的createBean方法无具体逻辑，由子类AbstractAutowireCapableBeanFactory具体实现
createBean的逻辑：
在doCreateBean方法中，进行bean的生命周期的管理，另外预留beanPostProcessor的执行入口，aop逻辑就是在beanPostProcessor的后置处理时进行的织入。
Object beanInstance = doCreateBean(beanName, mbdToUse, args);

```java
/**
	 * Central method of this class: creates a bean instance,
	 * populates the bean instance, applies post-processors, etc.
	 * @see #doCreateBean
	 */
	@Override
	protected Object createBean(String beanName, RootBeanDefinition mbd, @Nullable Object[] args)
			throws BeanCreationException {

		if (logger.isTraceEnabled()) {
			logger.trace("Creating instance of bean '" + beanName + "'");
		}
		RootBeanDefinition mbdToUse = mbd;

		// Make sure bean class is actually resolved at this point, and
		// clone the bean definition in case of a dynamically resolved Class
		// which cannot be stored in the shared merged bean definition.
		Class<?> resolvedClass = resolveBeanClass(mbd, beanName);
		if (resolvedClass != null && !mbd.hasBeanClass() && mbd.getBeanClassName() != null) {
			mbdToUse = new RootBeanDefinition(mbd);
			mbdToUse.setBeanClass(resolvedClass);
		}

		// Prepare method overrides.
		try {
			mbdToUse.prepareMethodOverrides();
		}
		catch (BeanDefinitionValidationException ex) {
			throw new BeanDefinitionStoreException(mbdToUse.getResourceDescription(),
					beanName, "Validation of method overrides failed", ex);
		}

		try {
			// Give BeanPostProcessors a chance to return a proxy instead of the target bean instance.
			Object bean = resolveBeforeInstantiation(beanName, mbdToUse);
			if (bean != null) {
				return bean;
			}
		}
		catch (Throwable ex) {
			throw new BeanCreationException(mbdToUse.getResourceDescription(), beanName,
					"BeanPostProcessor before instantiation of bean failed", ex);
		}

		try {
			Object beanInstance = doCreateBean(beanName, mbdToUse, args);
			if (logger.isTraceEnabled()) {
				logger.trace("Finished creating instance of bean '" + beanName + "'");
			}
			return beanInstance;
		}
		catch (BeanCreationException | ImplicitlyAppearedSingletonException ex) {
			// A previously detected exception with proper bean creation context already,
			// or illegal singleton state to be communicated up to DefaultSingletonBeanRegistry.
			throw ex;
		}
		catch (Throwable ex) {
			throw new BeanCreationException(
					mbdToUse.getResourceDescription(), beanName, "Unexpected exception during bean creation", ex);
		}
	}
```

doCreateBean方法中，进行bean的生命周期的管理，另外预留beanPostProcessor的执行入口，aop逻辑就是在beanPostProcessor的后置处理时进行的织入。
下面就是经典的bean的生命周期代码：

1. 实例化bean实例

2. 设置属性值

3. 初始化阶段

   1. aware接口

   2. BeanPostProcessor的前置处理

   3. initializingBean接口的afterPropertySet方法

   4. Init-method 方法调用

   5. BeanPostProcessor的后置处理

4. 注册destruntion回调接口

5. 运行使用

6. 是否实现DisposableBean接口，执行destroy方法

7. 是否配置自定义destory方法

这个地方考点太多，涉及三级缓存及循环依赖的解决等，回头仔细分析
先分析初始化方法initializeBean
exposedObject = initializeBean(beanName, exposedObject, mbd)

```java
protected Object doCreateBean(final String beanName, final RootBeanDefinition mbd, final @Nullable Object[] args)
			throws BeanCreationException {

		// Instantiate the bean.
		BeanWrapper instanceWrapper = null;
		if (mbd.isSingleton()) {
			instanceWrapper = this.factoryBeanInstanceCache.remove(beanName);
		}
		if (instanceWrapper == null) {
			instanceWrapper = createBeanInstance(beanName, mbd, args);  
		}
		final Object bean = instanceWrapper.getWrappedInstance();
		Class<?> beanType = instanceWrapper.getWrappedClass();
		if (beanType != NullBean.class) {
			mbd.resolvedTargetType = beanType;
		}

		// Allow post-processors to modify the merged bean definition.
		synchronized (mbd.postProcessingLock) {
			if (!mbd.postProcessed) {
				try {
					applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName);
				}
				catch (Throwable ex) {
					throw new BeanCreationException(mbd.getResourceDescription(), beanName,
							"Post-processing of merged bean definition failed", ex);
				}
				mbd.postProcessed = true;
			}
		}

		// Eagerly cache singletons to be able to resolve circular references
		// even when triggered by lifecycle interfaces like BeanFactoryAware.
		boolean earlySingletonExposure = (mbd.isSingleton() && this.allowCircularReferences &&
				isSingletonCurrentlyInCreation(beanName));
		if (earlySingletonExposure) {
			if (logger.isTraceEnabled()) {
				logger.trace("Eagerly caching bean '" + beanName +
						"' to allow for resolving potential circular references");
			}
			addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, mbd, bean));
		}

		// Initialize the bean instance.
		Object exposedObject = bean;
		try {
			populateBean(beanName, mbd, instanceWrapper);      ---设置属性
			exposedObject = initializeBean(beanName, exposedObject, mbd);  ---初始化属性
		}
		catch (Throwable ex) {
			if (ex instanceof BeanCreationException && beanName.equals(((BeanCreationException) ex).getBeanName())) {
				throw (BeanCreationException) ex;
			}
			else {
				throw new BeanCreationException(
						mbd.getResourceDescription(), beanName, "Initialization of bean failed", ex);
			}
		}

		if (earlySingletonExposure) {
			Object earlySingletonReference = getSingleton(beanName, false);
			if (earlySingletonReference != null) {
				if (exposedObject == bean) {
					exposedObject = earlySingletonReference;
				}
				else if (!this.allowRawInjectionDespiteWrapping && hasDependentBean(beanName)) {
					String[] dependentBeans = getDependentBeans(beanName);
					Set<String> actualDependentBeans = new LinkedHashSet<>(dependentBeans.length);
					for (String dependentBean : dependentBeans) {
						if (!removeSingletonIfCreatedForTypeCheckOnly(dependentBean)) {
							actualDependentBeans.add(dependentBean);
						}
					}
					if (!actualDependentBeans.isEmpty()) {
						throw new BeanCurrentlyInCreationException(beanName,
								"Bean with name '" + beanName + "' has been injected into other beans [" +
								StringUtils.collectionToCommaDelimitedString(actualDependentBeans) +
								"] in its raw version as part of a circular reference, but has eventually been " +
								"wrapped. This means that said other beans do not use the final version of the " +
								"bean. This is often the result of over-eager type matching - consider using " +
								"'getBeanNamesOfType' with the 'allowEagerInit' flag turned off, for example.");
					}
				}
			}
		}

		// Register bean as disposable.
		try {
			registerDisposableBeanIfNecessary(beanName, bean, mbd);
		}
		catch (BeanDefinitionValidationException ex) {
			throw new BeanCreationException(
					mbd.getResourceDescription(), beanName, "Invalid destruction signature", ex);
		}

		return exposedObject;
	}
```
initializeBean，初始化方法，回忆一下生命周期总结中，初始化方法做了什么

初始化阶段

1. aware接口

2. BeanPostProcessor的前置处理

3. initializingBean接口的afterPropertySet方法

4. Init-method 方法调用

5. BeanPostProcessor的后置处理

从invokeAwareMethods方法可以看出，这里的aware接口，只处理三种类型BeanNameAware，BeanClassLoaderAware，BeanFactoryAware

从invokeInitMethods可以看出，init方法先执行isInitializingBean的afterPropertiesSet方法，如果不是isInitializingBean的话，再执行自定义的初始化方法。



```java
protected Object initializeBean(final String beanName, final Object bean, @Nullable RootBeanDefinition mbd) {
		if (System.getSecurityManager() != null) {
			AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
				invokeAwareMethods(beanName, bean);
				return null;
			}, getAccessControlContext());
		}
		else {
			invokeAwareMethods(beanName, bean);
		}

		Object wrappedBean = bean;
		if (mbd == null || !mbd.isSynthetic()) {
			wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
		}

		try {
			invokeInitMethods(beanName, wrappedBean, mbd);
		}
		catch (Throwable ex) {
			throw new BeanCreationException(
					(mbd != null ? mbd.getResourceDescription() : null),
					beanName, "Invocation of init method failed", ex);
		}
		if (mbd == null || !mbd.isSynthetic()) {
			wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
		}

		return wrappedBean;
	}

private void invokeAwareMethods(final String beanName, final Object bean) {
		if (bean instanceof Aware) {
			if (bean instanceof BeanNameAware) {
				((BeanNameAware) bean).setBeanName(beanName);
			}
			if (bean instanceof BeanClassLoaderAware) {
				ClassLoader bcl = getBeanClassLoader();
				if (bcl != null) {
					((BeanClassLoaderAware) bean).setBeanClassLoader(bcl);
				}
			}
			if (bean instanceof BeanFactoryAware) {
				((BeanFactoryAware) bean).setBeanFactory(AbstractAutowireCapableBeanFactory.this);
			}
		}
	}

protected void invokeInitMethods(String beanName, final Object bean, @Nullable RootBeanDefinition mbd)
			throws Throwable {

		boolean isInitializingBean = (bean instanceof InitializingBean);
		if (isInitializingBean && (mbd == null || !mbd.isExternallyManagedInitMethod("afterPropertiesSet"))) {
			if (logger.isTraceEnabled()) {
				logger.trace("Invoking afterPropertiesSet() on bean with name '" + beanName + "'");
			}
			if (System.getSecurityManager() != null) {
				try {
					AccessController.doPrivileged((PrivilegedExceptionAction<Object>) () -> {
						((InitializingBean) bean).afterPropertiesSet();
						return null;
					}, getAccessControlContext());
				}
				catch (PrivilegedActionException pae) {
					throw pae.getException();
				}
			}
			else {
				((InitializingBean) bean).afterPropertiesSet();
			}
		}

		if (mbd != null && bean.getClass() != NullBean.class) {
			String initMethodName = mbd.getInitMethodName();
			if (StringUtils.hasLength(initMethodName) &&
					!(isInitializingBean && "afterPropertiesSet".equals(initMethodName)) &&
					!mbd.isExternallyManagedInitMethod(initMethodName)) {
				invokeCustomInitMethod(beanName, bean, mbd);
			}
		}
	}
```

### 三级缓存

singleton的三级缓存，先从第一个getSigleton方法看，
三级缓存：
singletonObjects      
Object singletonObject = this.singletonObjects.get(beanName);
earlySingletonObjects
singletonObject = this.earlySingletonObjects.get(beanName);
singletonFactory
singletonObject = singletonFactory.getObject();

```java
/**
	 * Return the (raw) singleton object registered under the given name.
	 * <p>Checks already instantiated singletons and also allows for an early
	 * reference to a currently created singleton (resolving a circular reference).
	 * @param beanName the name of the bean to look for
	 * @param allowEarlyReference whether early references should be created or not
	 * @return the registered singleton object, or {@code null} if none found
	 */
	@Nullable
	protected Object getSingleton(String beanName, boolean allowEarlyReference) {
		Object singletonObject = this.singletonObjects.get(beanName);
		if (singletonObject == null && isSingletonCurrentlyInCreation(beanName)) {
			synchronized (this.singletonObjects) {
				singletonObject = this.earlySingletonObjects.get(beanName);
				if (singletonObject == null && allowEarlyReference) {
					ObjectFactory<?> singletonFactory = this.singletonFactories.get(beanName);
					if (singletonFactory != null) {
						singletonObject = singletonFactory.getObject();
						this.earlySingletonObjects.put(beanName, singletonObject);
						this.singletonFactories.remove(beanName);
					}
				}
			}
		}
		return singletonObject;
	}
```

### 循环依赖如何解决








## spring aop速记

参考博客：[读懂Spring AOP面向切面编程](https://juejin.cn/post/6844903679489736711 )
[从@EnableAspectJAutoProxy分析Spring AOP原理](https://blog.csdn.net/zhoushimiao1990/article/details/89853368)



spring 的aop是依赖于ioc的，初始化后中的BeanPostProcesso方法执行时进行织入
以注解方式的aop为例，开启spring aop需要在configuration配置类上添加@EnableAspectJAutoProxy注解
而EnableAspectJAutoProxy注解会首先将AspectJAutoProxyRegistrar引入容器中
而AspectJAutoProxyRegistrar会向容器中注入AnnotationAwareAspectJAutoProxyCreator的beanDefination，继而注入一个bean

```java
//proxyTargetClass 定义，引入AspectJAutoProxyRegistrar
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({AspectJAutoProxyRegistrar.class})
public @interface EnableAspectJAutoProxy {
    boolean proxyTargetClass() default false;

    boolean exposeProxy() default false;
}

//AspectJAutoProxyRegistrar的实现，像容器中注入一个AspectJAnnotationAutoProxyCreator
class AspectJAutoProxyRegistrar implements ImportBeanDefinitionRegistrar {
    AspectJAutoProxyRegistrar() {
    }

    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AopConfigUtils.registerAspectJAnnotationAutoProxyCreatorIfNecessary(registry);
        AnnotationAttributes enableAspectJAutoProxy = AnnotationConfigUtils.attributesFor(importingClassMetadata, EnableAspectJAutoProxy.class);
        if (enableAspectJAutoProxy != null) {
            if (enableAspectJAutoProxy.getBoolean("proxyTargetClass")) {
                AopConfigUtils.forceAutoProxyCreatorToUseClassProxying(registry);
            }

            if (enableAspectJAutoProxy.getBoolean("exposeProxy")) {
                AopConfigUtils.forceAutoProxyCreatorToExposeProxy(registry);
            }
        }

    }
}

//
@Nullable
	public static BeanDefinition registerAspectJAnnotationAutoProxyCreatorIfNecessary(
			BeanDefinitionRegistry registry, @Nullable Object source) {

		return registerOrEscalateApcAsRequired(AnnotationAwareAspectJAutoProxyCreator.class, registry, source);
	}

```

AnnotationAwareAspectJAutoProxyCreator的定义如下：
是AspectJAwareAdvisorAutoProxyCreator的一个实现子类
AspectJAwareAdvisorAutoProxyCreator就是我们要找的BeanPostProcessor，会在ioc加载benn的时候，进行动态代理的代码织入

```java
public class AnnotationAwareAspectJAutoProxyCreator extends AspectJAwareAdvisorAutoProxyCreator {}
```
AspectJAwareAdvisorAutoProxyCreator的类图如下：

![](/Users/liyun/Documents/截屏2022-02-28 11.08.07.png)

很明显看出，他是一个BeanPostProcessor，BeanPostProcessor接口的定义如下

```java
public interface BeanPostProcessor {
    @Nullable
    default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Nullable
    default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
```
而这个AnnotationAwareAspectJAutoProxyCreator的后置处理器方法在父类AbstractAutoProxyCreator中实现如下

```java
public Object postProcessAfterInitialization(@Nullable Object bean, String beanName) {
        if (bean != null) {
            Object cacheKey = this.getCacheKey(bean.getClass(), beanName);
            if (this.earlyProxyReferences.remove(cacheKey) != bean) {
                return this.wrapIfNecessary(bean, beanName, cacheKey);
            }
        }

        return bean;
 }

//ioc中讲过doCreateBean中的初始化Bean方法，涉及BeanPostProcessor的调用
protected Object initializeBean(final String beanName, final Object bean, @Nullable RootBeanDefinition mbd) {
		if (System.getSecurityManager() != null) {
			AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
				invokeAwareMethods(beanName, bean);
				return null;
			}, getAccessControlContext());
		}
		else {
			invokeAwareMethods(beanName, bean);
		}

		Object wrappedBean = bean;
		if (mbd == null || !mbd.isSynthetic()) {
			wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
		}

		try {
			invokeInitMethods(beanName, wrappedBean, mbd);
		}
		catch (Throwable ex) {
			throw new BeanCreationException(
					(mbd != null ? mbd.getResourceDescription() : null),
					beanName, "Invocation of init method failed", ex);
		}
		if (mbd == null || !mbd.isSynthetic()) {
			wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
		}

		return wrappedBean;
	}
//applyBeanPostProcessorsAfterInitialization 方法展开
public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;

        Object current;
        for(Iterator var4 = this.getBeanPostProcessors().iterator(); var4.hasNext(); result = current) {
            BeanPostProcessor processor = (BeanPostProcessor)var4.next();
            current = processor.postProcessAfterInitialization(result, beanName);
            if (current == null) {
                return result;
            }
        }

        return result;
    }
```

这个方法后置处理方法postProcessAfterInitialization就会在Bean的初始化阶段，顺序执行所有的BeanPostProcessor的时候，执行到AnnotationAwareAspectJAutoProxyCreator的后置处理器方法，进行动态代理织入，织入方法就是wrapIfNecessary，

spring AOP的重点方法来了：
spring在aop织入前会有一些准备工作，将我们配置的aspect解析成一个advise，
会首先排除掉不需要代理的类，然后将已经加载的Bean中扫描所有的advice和advisor

Object[] specificInterceptors = this.getAdvicesAndAdvisorsForBean(bean.getClass(), beanName, (TargetSource)null);

进行代理类的生成：

Object proxy = this.createProxy(bean.getClass(), beanName, specificInterceptors, new SingletonTargetSource(bean));

```java
protected Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {
        if (StringUtils.hasLength(beanName) && this.targetSourcedBeans.contains(beanName)) {
            return bean;
        } else if (Boolean.FALSE.equals(this.advisedBeans.get(cacheKey))) {
            return bean;
        } else if (!this.isInfrastructureClass(bean.getClass()) && !this.shouldSkip(bean.getClass(), beanName)) {
          //扫描所有的advice和advisor
         

            Object[] specificInterceptors = this.getAdvicesAndAdvisorsForBean(bean.getClass(), beanName, (TargetSource)null);
            if (specificInterceptors != DO_NOT_PROXY) {
                this.advisedBeans.put(cacheKey, Boolean.TRUE);
              //代理类的生成
              // beanClass，加载到Spring，触发AOP的bean类
//targetSource，目标对象，示例中则是从ctx中取出的testBean
//specificInterceptors，指定Advisor，示例中则是before和after的方法。
                Object proxy = this.createProxy(bean.getClass(), beanName, specificInterceptors, new SingletonTargetSource(bean));
                this.proxyTypes.put(cacheKey, proxy.getClass());
                return proxy;
            } else {
                this.advisedBeans.put(cacheKey, Boolean.FALSE);
                return bean;
            }
        } else {
            this.advisedBeans.put(cacheKey, Boolean.FALSE);
            return bean;
        }
    }
```

通过ProxyFactory，来或得代理，把所需的参数都给ProxyFactory

```java
protected Object createProxy(Class<?> beanClass, @Nullable String beanName,
			@Nullable Object[] specificInterceptors, TargetSource targetSource) {

		if (this.beanFactory instanceof ConfigurableListableBeanFactory) {
			AutoProxyUtils.exposeTargetClass((ConfigurableListableBeanFactory) this.beanFactory, beanName, beanClass);
		}

		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.copyFrom(this);

		if (!proxyFactory.isProxyTargetClass()) {
			if (shouldProxyTargetClass(beanClass, beanName)) {
				proxyFactory.setProxyTargetClass(true);
			}
			else {
				evaluateProxyInterfaces(beanClass, proxyFactory);
			}
		}

		Advisor[] advisors = buildAdvisors(beanName, specificInterceptors);
		proxyFactory.addAdvisors(advisors);
		proxyFactory.setTargetSource(targetSource);
		customizeProxyFactory(proxyFactory);

		proxyFactory.setFrozen(this.freezeProxy);
		if (advisorsPreFiltered()) {
			proxyFactory.setPreFiltered(true);
		}
		// 是JDK代理还是Cglib代理
		return proxyFactory.getProxy(getProxyClassLoader());
	}
```

下面是具体的选择代理类生成，是JDK代理还是Cglib代理

有实现接口，就jdk动态代理，没有就cglib

```java
public Object getProxy(@Nullable ClassLoader classLoader) {
		return createAopProxy().getProxy(classLoader);
	}
//createAopProxy() 判断是JDK代理还是Cglib代理
protected final synchronized AopProxy createAopProxy() {
		if (!this.active) {
			activate();
		}
		return getAopProxyFactory().createAopProxy(this);
	}
//DefaultAopProxyFactory类的createAopProxy实现了createAopProxy方法，下面就是
@Override
	public AopProxy createAopProxy(AdvisedSupport config) throws AopConfigException {
		if (config.isOptimize() || config.isProxyTargetClass() || hasNoUserSuppliedProxyInterfaces(config)) {
			Class<?> targetClass = config.getTargetClass();
			if (targetClass == null) {
				throw new AopConfigException("TargetSource cannot determine target class: " +
						"Either an interface or a target is required for proxy creation.");
			}
			if (targetClass.isInterface() || Proxy.isProxyClass(targetClass)) {
				return new JdkDynamicAopProxy(config);
			}
			return new ObjenesisCglibAopProxy(config);
		}
		else {
			return new JdkDynamicAopProxy(config);
		}
	}
```



## Java动态代理

回忆一下，很久没看动态代理了



## mybatis 源码速记



## JUC源码速记




```

```

## Redis速记
[redis基本知识](https://blog.csdn.net/weixin_42174176/article/details/112239648)

## Redis基本知识

### redis实现队列
使用list，lpush入队，brpop 带阻塞出队
brpop的时候，启动一个线程或者多个线程，while死循环的包裹brpop，等待出队，brpop监听topic,有数据会后立即处理，处理后循环进入下一个数据出队，





```java
//数据放队列
if(null != byteKey && byteValue !=null){
                jedis = jedisTool.getJedis();
                jedis.lpush(byteKey,byteValue);
            }

// 队列等待接收数据
public abstract class AbstractRedisListener implements Runnable {

    public JedisTool jedisTool=SpringTools.getBean("jedisTool",JedisTool.class);

    private final Logger logger=LoggerFactory.getLogger(AbstractRedisListener.class);

    @SuppressWarnings("deprecation")
    @Override
    public void run() {
        String queue_name=initQueueName();
        byte[] queue_name_bytes=object2Bytes(queue_name);
        Jedis jedis=jedisTool.getJedis();
        while(true){

            logger.info("接收redis队列消息,队列名称:"+queue_name);
            List<byte[]> bytes=null;
            bytes=jedis.brpop(queue_name_bytes);
            logger.info("队列获取消息完成");
            if(bytes!=null){
                byte[] bs1=bytes.get(0);
                byte[] bs2=bytes.get(1);
                if(bs1!=null&&bs2!=null){
                    logger.info("消息内容："+bytes2Object(bs2));
                    handleMessage(bs2);
                }
            }
        }



    }
```

### Redis实现延迟队列

zset集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是 O(1)。 集合中最大的成员数为 2^32 - 1 (4294967295, 每个集合可存储40多亿个成员)

ZADD key score1 member1 [score2 member2]
向有序集合添加一个或多个成员，或者更新已存在成员的分数
ZSCORE key member
返回有序集中，成员的分数值

HSET key field value
将哈希表 key 中的字段 field 的值设为 value 。

HGET key field
获取存储在哈希表中指定字段的值。
HEXISTS key field
查看哈希表 key 中，指定的字段是否存在。

```java
//这是一个设置方法的参考，将消息的唯一标识先放到zset里，队列名称当做集合名称，消息id作为集合里的成员变量，延迟时间戳当做分数，
// 在hash中，以消息id当做hash的key，将消息内容放到hash里。

result = DelayQueueRedisUtil.add(delayQueueMessage.getQueueName(), realMessageId, JSONObject.toJSONString(delayQueueMessage), calendar.getTimeInMillis());


public static boolean add(String redisKey, String field, String value, long millisecond) {
        Jedis jedis = redisClusterTool.getJedis();

        try {
            Long result1 = jedis.zadd("DQ_SET_" + redisKey, (double)millisecond, field);
            Long result2 = jedis.hset("DQ_HASH_" + redisKey, field, value);
            if (result1 != null && result2 != null && result1 > 0L && result2 > 0L) {
                boolean var8 = true;
                return var8;
            }
        } catch (Exception var19) {
            logger.error(var19.getMessage(), var19);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception var18) {
                    logger.error(var18.getMessage(), var18);
                }
            }

        }
```
**删除延迟队列里消息的时候**：
[ ZRANGE key start stop [WITHSCORES\]](https://www.runoob.com/redis/sorted-sets-zrange.html) 通过索引区间返回有序集合指定区间内的成员

[ZREM key member [member ...\]](https://www.runoob.com/redis/sorted-sets-zrem.html)
移除有序集合中的一个或多个成员

[HDEL key field1 [field2\]](https://www.runoob.com/redis/hashes-hdel.html)
删除一个或多个哈希表字段

```java
//监听延时队列的线程，死循环等待，有消息的时候马上循环，没有的时候，线程sleep睡一会儿在循环
public void run() {
        while(!Thread.interrupted()) {
            try {
              //轮训zset中过期的消息进行出路，获得消息的id，获取完，立马删除队列里对应的zset里的成员和对应的hash集合
                Set<DelayQueueMessage> messages = DelayQueue.poll(this.delayQueueName, 0.0D, (double)System.currentTimeMillis(), this.countOneTime);
                if (messages != null && messages.size() != 0) {
                    Iterator iterator = messages.iterator();

                    while(iterator.hasNext()) {
                        DelayQueueMessage message = (DelayQueueMessage)iterator.next();
                        if (DelayQueue.remove(message)) {
                            this.execDelayQueueHandler.doConsumerQueue(message);
                        }
                    }
                } else {//没有消息的时候，线程sleep睡一会儿在循环
                  
                    if (this.printCount > this.printCountLimit) {
                        this.logger.info("未查询到队列消息, 等待{}s [每间隔{}s打印一次]", this.sleepTimeMillis / 1000L, (long)this.printCountLimit * this.sleepTimeMillis / 1000L);
                        this.printCount = 0;
                    }

                    this.sleepSomeTime(this.sleepTimeMillis);
                    ++this.printCount;
                }
            } catch (Exception var4) {
                this.logger.error("队列消息处理失败：{}", var4);
                this.sleepSomeTime(this.sleepTimeMillis);
            }
        }

    }

// 下面是删除队列里对应的zset里的成员和对应的hash集合
public static boolean remove(DelayQueueMessage delayQueueMessage) {
        String realMessageId = delayQueueMessage.getMessageType() + delayQueueMessage.getMessageId();
        boolean result = DelayQueueRedisUtil.delete(delayQueueMessage.getQueueName(), realMessageId);
        logger.info("DelayQueue消息删除完成，消息：{}, result：{}", delayQueueMessage, result);
        return result;
    }
//下面是操作一个redis，操作后，关闭redis
public static boolean delete(String redisKey, String field) {
        Jedis jedis = redisClusterTool.getJedis();

        boolean var5;
        try {
            Long result1 = jedis.hdel("DQ_HASH_" + redisKey, field);
            Long result2 = jedis.zrem("DQ_SET_" + redisKey, new String[]{field});
            if (result1 == null || result2 == null || result1 <= 0L || result2 <= 0L) {
                return false;
            }

            var5 = true;
        } catch (Exception var16) {
            logger.error(var16.getMessage(), var16);
            return false;
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception var15) {
                    logger.error(var15.getMessage(), var15);
                }
            }

        }

        return var5;
    }
```




### Redis实现简单的分布式锁

Setex 命令 **SETEX key seconds value**

setex = set expire）：

 将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)。

 如果 key 已经存在， SETEX 命令将覆写旧值。

setex可能会存在覆盖锁的问题

SETNX key value
含义（setnx = SET if Not eXists）：

将 key 的值设为 value ，当且仅当 key 不存在。
若给定的 key 已经存在，则 SETNX 不做任何动作。
SETNX 是『SET if Not eXists』(如果不存在，则 SET)的简写。

返回值：
设置成功，返回 1 。
设置失败，返回 0 。

- **`PX milliseconds`** ： 将键的过期时间设置为 `milliseconds` 毫秒。 执行 `SET key value PX milliseconds` 的效果等同于执行 `PSETEX key milliseconds value` 。

SET key value NX  PX milliseconds   可以用于设锁，并同时设置过期时间







### redis实现布隆过滤器
基于guava的BloomFilter类，底层修改bitarray数组为redis的setbit  
setbit key offset value     offset是根绝redi值的最大范围，可以设置为512M，即2^32大小

setbit 和getbit都是操作字符串的命令

``` redis
redis> SETBIT bit 10086 1
(integer) 0

redis> GETBIT bit 10086
(integer) 1

redis> GETBIT bit 100   # bit 默认被初始化为 0
(integer) 0
```

#### guava的BloomFilter



### 分布式日志实现


### 限流实现
#### nginx限流
#### Tomcat限流
#### 服务端限流
- 滑动窗口算法
- 漏桶算法
- 令牌桶算法


