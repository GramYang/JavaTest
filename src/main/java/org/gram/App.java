package org.gram;

import org.gram.algorithms.AStar.AStarTest;
import org.gram.algorithms.Graph.GraphSearchTest;
import org.gram.algorithms.Graph.TopologicalSortingTest;
import org.gram.algorithms.MinimumSpanningTree.KruskalTest;
import org.gram.algorithms.Morris.MorrisTest;
import org.gram.algorithms.dynamicProgram.DP;
import org.gram.algorithms.greedy.GD;
import org.gram.cellnet.queue.CellnetQueueTest;
import org.gram.concurrent_test.*;
import org.gram.generics.GenericsTest;
import org.gram.gson.GsonTest;
import org.gram.jackson.JacksonTest;
import org.gram.java8.*;
import org.gram.reflect.*;
import org.gram.std.*;
import org.gram.t1.T1;
import org.gram.type.*;
import org.gram.type.statics.StaticTest;
import org.gram.websocket.WebSocketTest;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args){
        //算法
//        DP dp=new DP();
//        dp.dp1();
//        GD gd=new GD();
//        gd.huffmanTree();
//        GraphSearchTest gst=new GraphSearchTest();
//        gst.breadthFirstSearch();
//        gst.depthFirstSearch();
//        TopologicalSortingTest tst=new TopologicalSortingTest();
//        tst.t1();
//        KruskalTest kt=new KruskalTest();
//        kt.t1();
//        AStarTest aStar=new AStarTest();
//        aStar.t1();
//        MorrisTest mt=new MorrisTest();
//        mt.t1();

        //websocket
//        WebSocketTest webSocketTest = new WebSocketTest();
//        webSocketTest.t1();
        //gson
//        GsonTest gsonTest=new GsonTest();
//        gsonTest.t1();
//        gsonTest.t2();
//        gsonTest.t3();
//        jackson
//        JacksonTest jacksonTest=new JacksonTest();
//        jacksonTest.t1();
        //cellnet的java实现
        //阻塞消息队列
//        CellnetQueueTest cellnetQueueTest=new CellnetQueueTest();
//        cellnetQueueTest.c1();
//        cellnetQueueTest.c2();
        //loop，直接用java的TimerTask或者ScheduledThreadPoolExecutor就行！

        //basic
//        BasicTest bt=new BasicTest();
//        bt.t1();
//        SortTest st4=new SortTest();
//        st4.t1();
//        st4.t2();
//        VarTest v=new VarTest();
//        v.TypeTest();
//        v.BitOpt();
        AnnotationTest at=new AnnotationTest();
//        at.at1();
        at.at2();
//        FormatTest ft=new FormatTest();
//        ft.Format1();
//        StringTest st=new StringTest();
//        st.st1();
//        ClassTest ct=new ClassTest();
//        ct.t1();
//        ct.t2();
//        ct.t3();
//        ct.t4();
//        ct.t5();
//        ct.t6();
//        ArrayTest at2=new ArrayTest();
//        at2.t1();
//        IOTest iot=new IOTest();
//        iot.byteArrayStreamTest();
//        iot.fileTest();
//        iot.fileStreamTest1();
//        iot.fileStreamTest2();
//        iot.pipedStreamTest();
//        iot.filterStreamTest();
//        iot.byteBufferTest();
//        ClassLoaderTest clt=new ClassLoaderTest();
//        clt.c1();
//        clt.c2();
//        StaticTest staticTest=new StaticTest();
//        staticTest.t1();
//        ControlFlowTest cft=new ControlFlowTest();
//        cft.t1();
//        cft.t2();
//        GenericsTest gt=new GenericsTest();
//        gt.t1();
//        gt.t2();
//        ObjectTest objectTest=new ObjectTest();
//        objectTest.t1();

        //std
//        SyncTest st2=new SyncTest();
//        st2.st1();
//        st2.st2();
//        st2.st3();
//        st2.st4();
//        VolatileTest vt=new VolatileTest();
//        vt.vt1();
//        vt.vt2();
//        ThreadLocalTest tlt=new ThreadLocalTest();
//        tlt.tlt1();
//        ThreadTest tt2=new ThreadTest();
//        tt2.joinTest();
//        tt2.yieldTest();
//        SetTest hst=new SetTest();
//        hst.hashSetTest();
//        hst.treeSetTest();
//        PriorityQueueTest pqt=new PriorityQueueTest();
//        pqt.t1();
//        ByteBufferTest byteBufferTest=new ByteBufferTest();
//        byteBufferTest.t1();
//        byteBufferTest.t2();
//        HashMapTest hashMapTest=new HashMapTest();
//        hashMapTest.t1();
//        hashMapTest.t2();
//        hashMapTest.t3();
//        TreeMapTest treeMapTest=new TreeMapTest();
//        treeMapTest.t1();
//        ArrayListTest arrayListTest=new ArrayListTest();
//        arrayListTest.t1();
//        arrayListTest.t2();
//        TimeTest tt=new TimeTest();
//        tt.tt1();
//        tt.tt2();
//        tt.t3();

        //反射
//        ReflectionTest rt=new ReflectionTest();
//        rt.t1();
//        rt.t2();
//        rt.t3();
//        rt.t4();
//        ProxyTest proxyTest=new ProxyTest();
//        proxyTest.p1();

        //java8新特性
//        StreamTest streamTest=new StreamTest();
//        streamTest.st1();
//        OptionalTest optionalTest=new OptionalTest();
//        optionalTest.o1();
//        LambdaTest lambdaTest = new LambdaTest();
//        lambdaTest.l1();
//        FunctionTest functionTest = new FunctionTest();
//        functionTest.f1();

        //concurrent包工具测试
//        ConcurrentHashMapTest chmt=new ConcurrentHashMapTest();
//        chmt.chmt1();
//        CountDownLatchTest cdlt=new CountDownLatchTest();
//        cdlt.t1();
//        AtomicTest at1=new AtomicTest();
//        at1.at1();
//        PriorityBlockingQueueTest pbqt=new PriorityBlockingQueueTest();
//        pbqt.t1();
//        CyclicBarrierTest cbt=new CyclicBarrierTest();
//        cbt.t1();
//        ReentrantLockTest rlt=new ReentrantLockTest();
//        rlt.t1();
//        ReentrantReadWriteLockTest rrwlt=new ReentrantReadWriteLockTest();
//        rrwlt.t1();
//        rrwlt.t2();
//        SemaphoreTest st3=new SemaphoreTest();
//        st3.t1();
//        ThreadPoolExecutorTest tpet=new ThreadPoolExecutorTest();
//        tpet.t1();
//        ArrayBlockingQueueTest arrayBlockingQueueTest=new ArrayBlockingQueueTest();
//        arrayBlockingQueueTest.a1();
//        ConcurrentLinkedDequeTest concurrentLinkedDequeTest=new ConcurrentLinkedDequeTest();
//        concurrentLinkedDequeTest.c1();
//        ConcurrentLinkedQueueTest concurrentLinkedQueueTest=new ConcurrentLinkedQueueTest();
//        concurrentLinkedQueueTest.c1();
//        ConcurrentSkipListMapTest concurrentSkipListMapTest=new ConcurrentSkipListMapTest();
//        concurrentSkipListMapTest.c1();
//        ConcurrentSkipListSetTest concurrentSkipListSetTest=new ConcurrentSkipListSetTest();
//        concurrentSkipListSetTest.c1();
//        LinkedBlockingQueueTest linkedBlockingQueueTest=new LinkedBlockingQueueTest();
//        linkedBlockingQueueTest.l1();
//        linkedBlockingQueueTest.l2();
//        LinkedBlockingDequeTest linkedBlockingDequeTest=new LinkedBlockingDequeTest();
//        linkedBlockingDequeTest.l1();
//        LinkedTransferQueueTest linkedTransferQueueTest=new LinkedTransferQueueTest();
//        linkedTransferQueueTest.l1();
//        ScheduledThreadPoolExecutorTest scheduledThreadPoolExecutorTest=new ScheduledThreadPoolExecutorTest();
//        scheduledThreadPoolExecutorTest.s1();
//        CopyOnWriteArrayListTest copyOnWriteArrayListTest=new CopyOnWriteArrayListTest();
//        copyOnWriteArrayListTest.c1();
//        CopyOnWriteArraySetTest copyOnWriteArraySetTest=new CopyOnWriteArraySetTest();
//        copyOnWriteArraySetTest.c1();

        //循环引用测试，java不存在循环引用
//        T1 t1=new T1();
//        System.out.println(t1);

        //cellnet的java实现
        //阻塞消息队列
//        CellnetQueueTest cellnetQueueTest=new CellnetQueueTest();
//        cellnetQueueTest.c1();
//        cellnetQueueTest.c2();
        //loop，直接用java的TimerTask或者ScheduledThreadPoolExecutor就行！
    }
}
