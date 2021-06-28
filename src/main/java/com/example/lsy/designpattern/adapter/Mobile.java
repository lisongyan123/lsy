package com.example.lsy.designpattern.adapter;

interface V5Power {
    public int providerV5Power();
}

class V5PowerAdapter implements V5Power {
    private Power power;

    public V5PowerAdapter(Power power) {
        this.power = power;
    }

    public int providerV5Power() {
        int changePower = power.proderV220Power();
        System.out.println("开始充电选择220v电压");
        return 5;
    }
}

class Power {
    public int proderV220Power() {
        System.out.println("proderV220Power充电220v电压");
        return 220;
    }
}

class Mobile {
    public void inputPower(V5Power power) {
        power.providerV5Power();
        System.out.println("手机客户端是 ：providerV5Power");
    }

    public static void main(String[] args) {
        V5Power power = new V5PowerAdapter(new Power());
        Mobile mobile = new Mobile();
        mobile.inputPower(power);
    }
}

