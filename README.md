# 1 项目介绍

OpenGPT，一款AI聊天项目。该项目为SpringAI框架+Ollama+Llama3大模型的落地实现，使用SpringMVC作为Web框架，集成了MySQL、ElasticSearch作为数据存储引擎。

# 2 技术选型

Jdk版本：JDK21（SpringAI最低要求）

框架选择：SpringBoot、SpringAI、SpringMVC、Mybatis、SpringData、Druid

大模型：Llama3（当下最强开源大模型）、Ollama（大模型部署工具）

# 3 项目落地（略）

# 4 碰到的问题

## 4.1 想使用流式传输，结果碰到了一连串bug

SpringAI框架支持了大模型stream的流式输出，这样可以给用户持续输出响应，流式输出的返回结果是Flux。

介于项目几乎全是IO任务，恰好可以搭配SpringWebFLux+JDK21的虚拟线程，理论上可以极大的增加并发量。

但当我使用流式输出时总是报错，在多次尝试后发现，如果我发送两条相同的消息，框架就会报错（经过测试，并不是ollama与llama的问题），我设法在每条消息后面加上特殊无意义符号，使得bug发送的几率降到了0.4%。

新问题出现了，我如何保证消息发送给用户的时候，我还能拿到消息并且存储在数据库中，经过查找资料我发现了Flux类的一个方法：

```java
public final Flux<T> doOnNext(Consumer<? super T> onNext) {
	Objects.requireNonNull(onNext, "onNext");
	return doOnSignal(this, null, onNext, null, null, null, null, null);
}
```

这个方法可以在每次发送数据片后再次处理这个数据，这样就简单了我可以直接调用接口存入ElasticSearch，也可以调用异步编程，不影响用户体验，介于每次都是一片一片的数据，我更可以先将数据片都放入缓存池中，相同消息的数据片给与相同的唯一标识即可，等数据片全部发送完成，再设法存入es，方法我也找到了，Flux类的另一个方法：

```java
public final Flux<T> doOnComplete(Runnable onComplete) {
	Objects.requireNonNull(onComplete, "onComplete");
	return doOnSignal(this, null, null, null, onComplete, null, null, null);
}
```

这个方法可以在所有数据片全部发送完成后开启一个异步线程任务，我可以通过这个异步线程任务将缓存池中的此次全部响应都存储入es。一切都是那么的顺利，但接下来我碰到了一个巨大的bug，大到我放弃了流式传输。

前文说到，如果发送两条相同的消息，框架就会报错，让我没想到的是doOnNext(Consumer<? super T> onNext)的执行也属于第二次，一时间我不知道这是SpringAI的BUG还是SpringWebFlux的BUG......我只能放弃这个漂亮的规划、放弃虚拟线程、放弃NIO，基于SpringWeb使用BIO。

# 5 项目后续规划

## 5.1 流式传输

流式传输我还是想用的，假以时日，如果Spring官方解决了这个问题，我大概率会去将项目Web框架改为SpringWebFlux。

## 5.2 前端的实现

由于时间紧迫等等原因，前端界面我没有时间也不想（前端对我来说确实太过于煎熬和无聊以及艰难，后续我可能投入时间学习）去搭建vue项目，于是我采用了老框架＋Thymeleaf，为了项目的美观我后续可能投入大量时间搭建vue项目。

## 5.3 大模型

本次仅仅使用了80亿参数的大模型，后续有更强的机器，可能会使用更强的大模型对项目进行压测。

# 6 对AI大模型的看法

开源AI大模型实在是为感兴趣的人打了一剂强心针，尤其是我这种其他语言使用者，LLM几乎都是Python的杰作，我Java还没找到出路，压根不可能去学习Python。

2023年是AI元年，我从2022年年底就翻墙注册账号使用ChatGPT，后续接触了GPT-4、Gemini（谷歌）、NewBing（微软）、通义千问（阿里）、通义灵码（阿里）、文心一言（百度）等等AI聊天工具，StableDiffusion（AI绘画）、RVC（AI变声器）等等AI开源项目，我与AI可谓是有缘又无缘，我对它是无比感兴趣，奈何我的主编程语言和它不属于一个领域。

但Llama3的开源，无疑引发了程序界的轰动，80亿参数与700亿参数，小公司几乎只要微调大模型（Llama3发布一周后大模型微调框架直接冲上Github热榜）就满足了自己的需求，个人开发者也可以在8G以上内存的机器跑大模型，无数人估计都感谢死扎克伯格了哈哈......但开源大模型的发展可能也使得无数AI专业的学生前途迷茫（此处仅个人观点）。

不论如何，LLM的目的是造福人类，AI的发展还处于上升期，如果某天AI能调用AI，每个公司只需要完善自己的大模型，然后对外提供接口，那无疑会颠覆整个后端行业。

# 7 重构项目

2024年12月4日，重构了此项目，使用了SpringWebFlux框架、JDK21虚拟线程、本地缓存caffeine、并且使用了基于qwen2-8b微调后的新模型模型——扁鹊，更加规范的代码风格等等。