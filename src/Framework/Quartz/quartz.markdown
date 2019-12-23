# Quartz
> Quartz is a richly featured, open source job scheduling library that can be integrated within virtually any Java application - from the smallest stand-alone application to the largest e-commerce system
> Quartz是一个功能丰富的开源任务调度库，可以集成到几乎任何Java应用程序中——从最小的独立应用程序到最大的电子商务系统

## 1. Quartz的入门的核心部分
* Job： 具体的任务，也就是我们最终要执行的业务逻辑
* Trigger: 触发器，用于标识任务在什么时候执行
* Scheduler: 调度器，任务的容器，用于控制任务的执行、暂停、停止
## 2. Job、JobDetail
### 2.1 Job
> 任务接口、内部只有一个方法execute,也就是我们具体的业务逻辑要实现的方法，其中参数JobExecutionContext是Job上下文
```
public class HelloJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Hello Job!");
    }
}
```
### 2.2 JobDetail
> Scheduler具体执行的类，内部包含了Job.class的信息
```
JobDetail job = newJob(HelloJob.class).build();
```
## 3. Triggers
### 3.1 SimpleTrigger
> SimpleTrigger可以满足的调度需求是：在具体的时间点执行一次，或者在具体的时间点执行，并且以指定的间隔重复执行若干次。
### 3.2 CronTrigger
> 基于日历的触发器
### 3.2.1 Cron表达式
> Cron-Expressions用于配置CronTrigger的实例。Cron Expressions是由七个子表达式组成的字符串。这些子表达式用空格分隔: 秒 分 时 天 月 周 年
```
秒	是	0-59	            , - * /
分	是	0-59	            , - * /
时	是	0-23	            , - * /
日	是	1-31	            , - * ? / L W C
月	是	1-12 或 JAN-DEC	    , - * /
周	是	1-7  或 SUN-SAT	    , - * ? / L C #
年	否	空   或 1970-2099	, - * /
```
* , ：表示指定多个值
* - ：范围内的值
* * : 整个时间段
* / : 频率，n/m表示从n开始，间隔为m
* ? : 不确定的值
* L : 用在日表示一个月中的最后一天，用在周表示该月最后一个星期X
* W ：离给定日期最近的工作日(周一到周五)。LW:这两个字符可以连用，表示在某个月最后一个工作日，即最后一个星期五。
* # ：用于指定月份中的第几周的哪一天。例如，如果你指定周域的值为 6#3，它意思是某月的第三个周五 (6=星期五，#3意味着月份中的第三周)

```
CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0/1 * * * ?");
CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withSchedule(scheduleBuilder).build();
```
## 4. Scheduler
> Job与Trigger容器，用于控制在指定的时间调用指定的Job
```
SchedulerFactory schedFactory = new StdSchedulerFactory();
Scheduler scheduler = schedFactory.getScheduler();
scheduler.start();
CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
        .cronSchedule("0 0/1 * * * ?");
CronTrigger cronTrigger = TriggerBuilder.newTrigger()
        .withIdentity("myTrigger", "group1")
        .withSchedule(scheduleBuilder).build();
JobDetail job = newJob(HelloJob.class)
        .withIdentity("myJob", "group1")
        .build();
scheduler.scheduleJob(job,cronTrigger);
```
## 参考
1. https://www.w3cschool.cn/quartz_doc/quartz_doc-1xbu2clr.html
2. https://blog.csdn.net/zhuyiquan/article/details/81809160
3. https://juejin.im/post/5a0c08c5f265da4335624c8f




