package com.example.lsy.designpattern.state;

/**
 * 解决多练发的if-else问题，但是有多少状态就会有多少类，代码结构复杂
 */
public class StateTest {
    public static void main(String[] args) throws InterruptedException {
        for(int i = 0; i < 5; i++) {
            OrderContext orderContext = new OrderContext("user" + i);
            orderContext.toPay();
            orderContext.checkPay();
            orderContext.deliverGoods();
            orderContext.toBeReceive();
            orderContext.receive();
            orderContext.end();
            System.out.println("---------");
            Thread.sleep(1000);
        }
    }
}

abstract class State {
    abstract void pay();
    abstract void checkPay();
    abstract void deliverGoods();
    abstract void toBeReceive();
    abstract void receive();
    abstract void other();
}

class ToBePayState extends State {

    private OrderContext orderContext;

    public ToBePayState(OrderContext orderContext) {
        this.orderContext = orderContext;
    }

    @Override
    void pay() {
        System.out.println("用户已支付...");
        orderContext.setState(orderContext.getPaidState());
    }

    @Override
    void checkPay() {

    }

    @Override
    void deliverGoods() {
        System.out.println("未付款，不能发货");
    }

    @Override
    void toBeReceive() {

    }

    @Override
    void receive() {

    }

    @Override
    void other() {

    }
}

class PaidState extends State {
    private OrderContext orderContext;

    public PaidState(OrderContext orderContext) {
        this.orderContext = orderContext;
    }

    @Override
    void pay() {
        System.out.println("已支付");
    }

    @Override
    void checkPay() {
        System.out.println("确认支付成功...");
        orderContext.setState(orderContext.getDeliverGoodsState());
    }

    @Override
    void deliverGoods() {
        System.out.println("准备发货");
    }

    @Override
    void toBeReceive() {

    }

    @Override
    void receive() {

    }

    @Override
    void other() {

    }
}

class DeliverGoodsState extends State {
    private OrderContext orderContext;

    public DeliverGoodsState(OrderContext orderContext) {
        this.orderContext = orderContext;
    }

    @Override
    void pay() {
        System.out.println("已支付");
    }

    @Override
    void checkPay() {

    }

    @Override
    void deliverGoods() {
        System.out.println("物流发货...");
        orderContext.setState(orderContext.getGoodsReceivedState());
    }

    @Override
    void toBeReceive() {

    }

    @Override
    void receive() {

    }

    @Override
    void other() {

    }
}

class GoodsToBeReceivedState extends State {
    private OrderContext orderContext;

    public GoodsToBeReceivedState(OrderContext orderContext) {
        this.orderContext = orderContext;
    }

    @Override
    void pay() {

    }

    @Override
    void checkPay() {

    }

    @Override
    void deliverGoods() {

    }

    @Override
    void toBeReceive() {
        System.out.println("运输中...");
        orderContext.setState(orderContext.getGoodsReceivedState());
    }

    @Override
    void receive() {

    }

    @Override
    void other() {

    }
}

class GoodsReceivedState extends State {
    private OrderContext orderContext;

    public GoodsReceivedState(OrderContext orderContext) {
        this.orderContext = orderContext;
    }

    @Override
    void pay() {

    }

    @Override
    void checkPay() {

    }

    @Override
    void deliverGoods() {

    }

    @Override
    void toBeReceive() {

    }

    @Override
    void receive() {
        System.out.println("签收货物...");
        orderContext.setState(orderContext.getSuccessState());
    }

    @Override
    void other() {

    }
}


class SuccessState extends State {
    private OrderContext orderContext;

    public SuccessState(OrderContext orderContext) {
        this.orderContext = orderContext;
    }

    @Override
    void pay() {
        System.out.println("已支付");
    }

    @Override
    void checkPay() {

    }

    @Override
    void deliverGoods() {
        System.out.println("已收货");
    }

    @Override
    void toBeReceive() {

    }

    @Override
    void receive() {

    }

    @Override
    void other() {
        System.out.println("用户积分增加...");
    }
}

class OrderContext {

    private State state;
    private ToBePayState toBePayState = new ToBePayState(this);
    private PaidState paidState = new PaidState(this);
    private DeliverGoodsState deliverGoodsState = new DeliverGoodsState(this);
    private GoodsToBeReceivedState goodsToBeReceivedState = new GoodsToBeReceivedState(this);
    private GoodsReceivedState goodsReceivedState = new GoodsReceivedState(this);
    private SuccessState successState = new SuccessState(this);

    //初始化订单的时候状态设置待付款
    public OrderContext(String userName) {
        System.out.println("用户 " + userName +" 下单");
        this.state = toBePayState;
    }

    public void toPay(){
        toBePayState.pay();
    }

    public void checkPay(){
        paidState.checkPay();
    }

    public void deliverGoods(){
        deliverGoodsState.deliverGoods();
    }

    public void toBeReceive(){
        goodsToBeReceivedState.toBeReceive();
    }

    public void receive(){
        goodsReceivedState.receive();
    }

    public void end(){
        successState.other();
    }


    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public ToBePayState getToBePayState() {
        return toBePayState;
    }

    public void setToBePayState(ToBePayState toBePayState) {
        this.toBePayState = toBePayState;
    }

    public PaidState getPaidState() {
        return paidState;
    }

    public void setPaidState(PaidState paidState) {
        this.paidState = paidState;
    }

    public DeliverGoodsState getDeliverGoodsState() {
        return deliverGoodsState;
    }

    public void setDeliverGoodsState(DeliverGoodsState deliverGoodsState) {
        this.deliverGoodsState = deliverGoodsState;
    }

    public SuccessState getSuccessState() {
        return successState;
    }

    public void setSuccessState(SuccessState successState) {
        this.successState = successState;
    }

    public GoodsToBeReceivedState getGoodsToBeReceivedState() {
        return goodsToBeReceivedState;
    }

    public void setGoodsToBeReceivedState(GoodsToBeReceivedState goodsToBeReceivedState) {
        this.goodsToBeReceivedState = goodsToBeReceivedState;
    }

    public GoodsReceivedState getGoodsReceivedState() {
        return goodsReceivedState;
    }

    public void setGoodsReceivedState(GoodsReceivedState goodsReceivedState) {
        this.goodsReceivedState = goodsReceivedState;
    }
}
