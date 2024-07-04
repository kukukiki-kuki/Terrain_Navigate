package cn.shzu.softExam;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description 访问者模式
 * @date 2024/5/24 22:20:33
 */
import java.util.*;

// 定义访问者接口
interface LibraryVisitor {
    void visit(Book book);  // 访问图书
    void visit(Article article);  // 访问论文
    void printSum();  // 打印总页数
}

// 实现具体的访问者类，用于计算总页数
class LibrarySumPrintVisitor implements LibraryVisitor {
    private int sum = 0;

    public void visit(Book book) {
        sum = sum + book.getNumberOfPages();  // 累加图书页数
    }

    public void visit(Article article) {
        sum = sum + article.getNumberOfPages();  // 累加论文页数
    }

    public void printSum(){
        System.out.println("SUM = " + sum);  // 打印总页数
    }
}

// 定义文献接口，接受访问者
interface LibraryItemInterface {
    void accept(LibraryVisitor visitor);
}

// 实现论文类
class Article implements LibraryItemInterface {
    private String m_title;  // 论文标题
    private String m_author;  // 论文作者
    private int m_start_page;  // 起始页码
    private int m_end_page;  // 结束页码

    public Article(String p_author, String p_title, int p_start_page, int p_end_page) {
        m_title = p_title;
        m_author = p_author;
        m_start_page = p_start_page;
        m_end_page = p_end_page;
    }

    public int getNumberOfPages() {
        return m_end_page - m_start_page;  // 计算页数
    }

    public void accept(LibraryVisitor visitor) {
        visitor.visit(this);  // 接受访问者
    }
}

// 实现图书类
class Book implements LibraryItemInterface {
    private String m_title;  // 书名
    private String m_author;  // 作者
    private int m_pages;  // 页数

    public Book(String p_author, String p_title, int p_pages) {
        m_title = p_title;
        m_author = p_author;
        m_pages = p_pages;
    }

    public int getNumberOfPages() {
        return m_pages;  // 获取页数
    }

    public void accept(LibraryVisitor visitor) {
        visitor.visit(this);  // 接受访问者
    }
}

public class LibraryTest {
    public static void main(String[] args) {
        List<LibraryItemInterface> items = new ArrayList<>();
        items.add(new Book("Author1", "Book1", 540));  // 添加一本图书
        items.add(new Article("Author2", "Article1", 0, 25));  // 添加一篇论文
        items.add(new Article("Author3", "Article2", 0, 25));  // 添加另一篇论文

        LibrarySumPrintVisitor visitor = new LibrarySumPrintVisitor();  // 创建访问者

        for (LibraryItemInterface item : items) {
            item.accept(visitor);  // 接受访问者
        }

        visitor.printSum();  // 打印总页数
    }
}

