package cn.shzu.sundry;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description 软考
 * @date 2024/5/23 23:17:50
 */
class Invoice {
    public void printInvoice() {
        System.out.println("This is the content of the invoice!");
    }
}

class Decorator extends Invoice {
    protected Invoice ticket;

    public Decorator(Invoice t) {
        ticket = t;
    }

    public void printInvoice() {
        if (ticket != null) {
            ticket.printInvoice(); // 1
        }
    }
}

class HeadDecorator extends Decorator {
    public HeadDecorator(Invoice t) {
        super(t);
    }

    public void printInvoice() {
        System.out.println("This is the header of the invoice!");
        super.printInvoice(); // 2
    }
}

class FootDecorator extends Decorator {
    public FootDecorator(Invoice t) {
        super(t);
    }

    public void printInvoice() {
        super.printInvoice(); // 3
        System.out.println("This is the footnote of the invoice!");
    }
}

class Test {
    public static void main(String[] args) {
        Invoice t = new Invoice();
        Invoice ticket;
        ticket = new HeadDecorator(new FootDecorator(t)); // 4
        ticket.printInvoice();
        System.out.println("------------------");
        ticket = new FootDecorator(new HeadDecorator(null)); // 5
        ticket.printInvoice();
    }
}

