# Android进阶开发Demo说明

## 一、 IPC 跨进程通信

[IPC进程间通信 blog](http://vivianking6855.github.io/Android-IPC/)

跨进程通信方式有很多：Intent传递Bundle数据，共享文件，Binder，Intent传递Bundle数据,ContentProvider,Socket.

- Intent 方式较为简单，没有包含在示例中
- file文件在并发读写时会有问题。SharedPreferences多进模式下读写不可靠，不包含在示例中

### 1. static variable did't work well for multi-process

- MainActivity set new sUserId = 2; But SecondActivity get sUserId = 1;
- When start SecondActivity, it run in another process, Application onCreate will enter again.

### 2. Messenger 轻量级的IPC方案

### 3. AIDL

- Book.java 图书信息的类，实现Parcelable
- IBook.aidl 是Book类在AIDL中的声明
- IBookManager Book管理接口：添加，获取图书，监听图书变化
- INewBookListener : 新书通知
- BookManagerActivity: Manager UI
- BookManagerService: Service deal with command from client (run on alone process)

## 二、 RemoteViews

[RemoteViews blog](http://vivianking6855.github.io/Android-Nano-Tips-12-RemoteViews/)

逻辑如下：

- 有A,B 2个Activity分别运行在不同的进程中，一个A(MainActivity),一个B(ClientActivity)
- A接受通知，模拟通知栏的效果
- B可以不停的地发送通知栏消息（模拟消息）。通知方案选用Broadcast（系统是用Binder）

## 三、 Android Message机制

[Android Message blog](http://vivianking6855.github.io/Android-Message)

### 1. Work thread send message to Main thread

- Main thread create Handler，implement handleMessage()
- Work thread send message through handler to update UI. message will add to MessageQueue
- Then Looper will pick out message to Main thread to deal with in handleMessage

### 2. Main thread send message to Work thread

- Create work thread and it's handler and Looper (two ways)
- Main thread send message with handler

## 四、 多线程和线程池 

### 1. MultiThread: Android线程形态

[多线程和线程池 blog](http://vivianking6855.github.io/Multi-Thread/)

- AsyncTask 模拟下载文件。
    - AsyncTask的execute必须在UI线程调用
    - 不要直接调用onPreExecute, doInBackground等方法
    - 一个AsyncTask对象只能执行一次，即只能调用一次execute方法
- IntentService
    - 适合于高优先级的后台任务。
    - 所有task执行完毕后，IntentService会自动退出
    - 可用Messenger，Broadcast等方式更新UI
- 四个基本线程池的使用
    - newFixedThreadPool
    - newCachedThreadPool
    - newScheduledThreadPool
    - newSingleThreadExecutor

### 2. ThreadSync：线程同步和concurrent包

[Thread Sync blog](http://vivianking6855.github.io/Thread-Sync/)

- SyncBasic：synchronized 基本用法
- SyncAdvanced：await 实践
- ConcurrentCoreFeature： concurrent包几个常用类示例
- product文件夹：生产者-消费者模型，current包的BlockingQueue

## 五、 ImageCache： Image加载和缓存

[ImageCache blog](http://vivianking6855.github.io/Android-Bitmap-Cache/)

- ImageAdapter, 里面会调用mImageLoader.bindBitmap加载图片
- MainActivity, 主界面，GirdView show出从网上下载的图片
- SquareImageView, 方形的View
- com.vv.imagecache.imageloader, ImageLoader实现