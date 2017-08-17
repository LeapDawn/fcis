package g11.dto;

import java.util.List;

public class ResultModel<T> {

    private static final int DEFAULT_ROWS = 10;
    private static final int DEFAULT_CURRENT_PAGE = 1;

    private int total; // 总记录数
    private int pages; // 总页数
    private int rows; // 每页记录数
    private int currentPage; // 当前页码
    private List<T> data;

    public ResultModel() {

    }

    public ResultModel(int total, int rows, int currentPage, List<T> data) {
        super();
        this.total = total > 0 ? total : 0;
        this.rows = rows > 0 ? rows : DEFAULT_ROWS;
        this.pages = this.total % this.rows == 0 && this.total != 0 ? (this.total / this.rows)
                : (this.total / this.rows) + 1;
        this.currentPage = currentPage <= this.pages ? currentPage : this.pages;
        this.currentPage = this.currentPage > 0 ? this.currentPage : DEFAULT_CURRENT_PAGE;
        this.data = data;
    }

    public ResultModel(int total, int rows) {
        this.total = total > 0 ? total : 0;
        this.rows = rows > 0 ? rows : DEFAULT_ROWS;
        pages = this.total % this.rows == 0 && this.total != 0 ? (this.total / this.rows)
                : (this.total / this.rows) + 1;
        this.currentPage = DEFAULT_CURRENT_PAGE;
    }

    public ResultModel(int total, int rows, int currentPage) {
        this.total = total > 0 ? total : 0;
        this.rows = rows > 0 ? rows : DEFAULT_ROWS;
        this.pages = this.total % this.rows == 0 && this.total != 0 ? (this.total / this.rows)
                : (this.total / this.rows) + 1;
        this.currentPage = currentPage <= this.pages ? currentPage : this.pages;
        this.currentPage = this.currentPage > 0 ? this.currentPage : DEFAULT_CURRENT_PAGE;
    }

    public ResultModel(int total, int rows, int currentPage, String order,
                       String sort) {
        this.total = total > 0 ? total : 0;
        this.rows = rows > 0 ? rows : DEFAULT_ROWS;
        this.pages = this.total % this.rows == 0 && this.total != 0 ? (this.total / this.rows)
                : (this.total / this.rows) + 1;
        this.currentPage = currentPage <= this.pages ? currentPage : this.pages;
        this.currentPage = this.currentPage > 0 ? this.currentPage : DEFAULT_CURRENT_PAGE;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PageModel [total=" + total + ", pages=" + pages + ", rows="
                + rows + ", currentPage=" + currentPage + ", data=" + data
                + "]";
    }

}
