package application.models;

public class CDRModel {

    private int Id;
    private String Title;
    private String Author;
    private String Section;
    private int XAxis;
    private int YAxis;
    private String BarCode;
    private String Description;
    private boolean OnLoan;
    private String[] Headers;
    private String MessageHeader;

    public CDRModel(int id, String title, String author, String section, int XAxis, int YAxis, String barCode, String description, boolean onLoan) {
        Id = id;
        Title = title;
        Author = author;
        Section = section;
        this.XAxis = XAxis;
        this.YAxis = YAxis;
        BarCode = barCode;
        Description = description;
        OnLoan = onLoan;
    }
    public CDRModel(int id, String title, String author, String section, int XAxis, int YAxis, String barCode, String description) {
        Id = id;
        Title = title;
        Author = author;
        Section = section;
        this.XAxis = XAxis;
        this.YAxis = YAxis;
        BarCode = barCode;
        Description = description;
    }

    public CDRModel() {

    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getSection() {
        return Section;
    }

    public void setSection(String section) {
        Section = section;
    }

    public int getXAxis() {
        return XAxis;
    }

    public void setXAxis(int XAxis) {
        this.XAxis = XAxis;
    }

    public int getYAxis() {
        return YAxis;
    }

    public void setYAxis(int YAxis) {
        this.YAxis = YAxis;
    }

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String barCode) {
        BarCode = barCode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public boolean getOnLoan() {
        return OnLoan;
    }

    public void setOnLoan(boolean onLoan) {
        OnLoan = onLoan;
    }

    @Override
    public String toString() {
        return "CDRModel{" +
                "Id=" + Id +
                ", Title='" + Title + '\'' +
                ", Author='" + Author + '\'' +
                ", Section='" + Section + '\'' +
                ", XAxis=" + XAxis +
                ", YAxis=" + YAxis +
                ", BarCode='" + BarCode + '\'' +
                ", Description='" + Description + '\'' +
                ", OnLoan=" + OnLoan +
                '}';
    }

    public String toBarcodeString() {
        return BarCode + ":   \t " + Title;
    }

    public Object[] toArray() {
        Object[] objArray = {Id, Title, Author, Section, XAxis, YAxis,
                BarCode, Description, OnLoan};
        return objArray;
    }
}
