2026-11-18 02:03:34.345 20863-20969 WM-WorkerFactory        com.example.setlist                  E  Could not instantiate com.example.setlist.Data.BackupWorker
java.lang.NoSuchMethodException: com.example.setlist.Data.BackupWorker.<init> [class android.content.Context, class androidx.work.WorkerParameters]
at java.lang.Class.getConstructor0(Class.java:3379)
at java.lang.Class.getDeclaredConstructor(Class.java:3061)
at androidx.work.WorkerFactory.createWorkerWithDefaultFallback$fallbackToReflection(WorkerFactory.kt:87)
at androidx.work.WorkerFactory.createWorkerWithDefaultFallback(WorkerFactory.kt:96)
at androidx.work.impl.WorkerWrapper.runWorker(WorkerWrapper.kt:234)
at androidx.work.impl.WorkerWrapper.access$runWorker(WorkerWrapper.kt:67)
at androidx.work.impl.WorkerWrapper$launch$1$resolution$1.invokeSuspend(WorkerWrapper.kt:98)
at androidx.work.impl.WorkerWrapper$launch$1$resolution$1.invoke(Unknown Source:8)
at androidx.work.impl.WorkerWrapper$launch$1$resolution$1.invoke(Unknown Source:4)
at kotlinx.coroutines.intrinsics.UndispatchedKt.startUndispatchedOrReturn(Undispatched.kt:42)
at kotlinx.coroutines.BuildersKt__Builders_commonKt.withContext(Builders.common.kt:164)
at kotlinx.coroutines.BuildersKt.withContext(Unknown Source:1)
at androidx.work.impl.WorkerWrapper$launch$1.invokeSuspend(WorkerWrapper.kt:98)
at androidx.work.impl.WorkerWrapper$launch$1.invoke(Unknown Source:8)
at androidx.work.impl.WorkerWrapper$launch$1.invoke(Unknown Source:4)
at androidx.work.ListenableFutureKt$launchFuture$1$2.invokeSuspend(ListenableFuture.kt:42)
at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:34)
at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:101)
at androidx.work.impl.utils.SerialExecutorImpl$Task.run(SerialExecutorImpl.java:96)
at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1154)
at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:652)
at java.lang.Thread.run(Thread.java:1563)
2026-11-18 02:03:34.345 20863-20969 WM-WorkerWrapper        com.example.setlist                  E  Could not create Worker com.example.setlist.Data.BackupWorker

WorkManager tries to build the backup worker but couldnt, needed Hilt to pass the params but with default
startup it doesnt use hilt

I chose repository.artists.first() just because I thought that grabbing the first snapshot and stopping is the most efficient way to run

I chose the constraints because thats what it said in the assignment, but issues can be: no guarantee the phone won't go above low battery, if storage stays low it'll never run

If backup worker runs twice it'll just write 2 files since theyre named in milliseconds(?) they wont overwrite

The constraints could make periodic job run later because of the issues mentions earlier.