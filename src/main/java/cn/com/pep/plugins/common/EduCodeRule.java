package cn.com.pep.plugins.common;


public class EduCodeRule {
    private final String ruleCode = "1";// 规则码
    private String eduCode;
    private EduCodeFactor rkxdFactor = new EduCodeFactor("rkxd",1, 1, 1, null);// 学段
    private EduCodeFactor zxxkcFactor = new EduCodeFactor("zxxkc", 2,2, 2, null);// 学科
    private EduCodeFactor publisherFactor = new EduCodeFactor("publisher",4, 3, 3, null);// 出版社
    private EduCodeFactor njFactor = new EduCodeFactor("nj",7, 1, 4, null);// 年级
    private EduCodeFactor fasciculeFactor = new EduCodeFactor("fascicule",8, 2, 5, null);// 册别
    private EduCodeFactor yearFactor = new EduCodeFactor("year",10, 2, 6, null);// 年份
    private EduCodeFactor editionFactor = new EduCodeFactor("edition",12, 1, 7, null);// 小版本
    private EduCodeFactor bookFactor = new EduCodeFactor("bookFactor",0, 19, 0, null);// 书籍因子 书籍Id或者章节Id
    private EduCodeFactor chapterCascadeFactor = new EduCodeFactor("chapterCascade",13, 6, 0, null);// 书籍因子 书籍Id或者章节Id
    private EduCodeFactor eduCodeFactor = new EduCodeFactor("eduCode",0, 13, 0, null);// 书籍因子 书籍Id或者章节Id

    public EduCodeRule() {
    }

    public EduCodeRule(String eduCode) {
        this.eduCode = eduCode;
        this.analyzeEduCode();
    }

    public void analyzeEduCode() {
        int startIndex = 1;
        if (this.getEduCode() != null && this.getEduCode().length() >= 13) {
            this.setEduCodeFactor(this.getEduCode().substring(0,eduCodeFactor.getLength()));
            String rkxd = this.getEduCode().substring(startIndex, startIndex + this.getRkxdFactor().getLength());
            this.setRkxdFactorCode(rkxd);
            startIndex += this.getRkxdFactor().getLength();
            this.setZxxkcFactorCode(this.getEduCode().substring(startIndex, startIndex + this.getZxxkcFactor().getLength()));
            startIndex += this.getZxxkcFactor().getLength();
            this.setPublisherFactorCode(this.getEduCode().substring(startIndex, startIndex + this.getPublisherFactor().getLength()));
            startIndex += this.getPublisherFactor().getLength();
            this.setNjFactorCode(rkxd + this.getEduCode().substring(startIndex, startIndex + this.getNjFactor().getLength()));
            startIndex += this.getNjFactor().getLength();
            this.setFasciculeFactorCode(this.getEduCode().substring(startIndex, startIndex + this.getFasciculeFactor().getLength()));
            startIndex += this.getFasciculeFactor().getLength();
            this.setYearCode(this.getEduCode().substring(startIndex, startIndex + this.getYearFactor().getLength()));
            startIndex += this.getYearFactor().getLength();
            this.setEditionFactorCode(this.getEduCode().substring(startIndex, startIndex + this.getEditionFactor().getLength()));
        }
        if (this.getEduCode() != null && this.getEduCode().length() >= 19){
            this.setBookFactor(this.getEduCode().substring(0,bookFactor.getLength()));
            this.setChapterCascadeFactor(this.getEduCode().substring(chapterCascadeFactor.getStartIndex(),chapterCascadeFactor.getStartIndex() + chapterCascadeFactor.getLength()));
        }
    }

    public static void main(String[] args) {
        EduCodeRule rule = new EduCodeRule("1212001502135090000111111");
        System.out.println(rule.getRkxdFactor().getCode());
        System.out.println(rule.getNjFactor().getCode());
    }


    public EduCodeFactor getRkxdFactor() {
        return rkxdFactor;
    }

    public void setRkxdFactorCode(String rkxd) {
        this.rkxdFactor.setCode(rkxd);
    }

    public EduCodeFactor getZxxkcFactor() {
        return zxxkcFactor;
    }

    public void setZxxkcFactorCode(String zxxkc) {
        this.zxxkcFactor.setCode(zxxkc);
    }

    public EduCodeFactor getPublisherFactor() {
        return publisherFactor;
    }

    public void setPublisherFactorCode(String publisher) {
        this.publisherFactor.setCode(publisher);
    }

    public EduCodeFactor getNjFactor() {
        return njFactor;
    }

    public void setNjFactorCode(String nj) {
        this.njFactor.setCode(nj);
    }

    public EduCodeFactor getFasciculeFactor() {
        return fasciculeFactor;
    }

    public void setFasciculeFactorCode(String fascicule) {
        this.fasciculeFactor.setCode(fascicule);
    }

    public EduCodeFactor getYearFactor() {
        return yearFactor;
    }

    public void setYearCode(String year) {
        this.yearFactor.setCode(year);
    }

    public EduCodeFactor getEditionFactor() {
        return editionFactor;
    }

    public void setEditionFactorCode(String edition) {
        this.editionFactor.setCode(edition);
    }


    public EduCodeFactor getBookFactor() {
        return bookFactor;
    }


    public void setBookFactor(String bookFactor) {
        this.bookFactor.setCode(bookFactor);
    }

    public String getEduCode() {
        return eduCode;
    }

    public void setEduCode(String eduCode) {
        this.eduCode = eduCode;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRkxdFactor(EduCodeFactor rkxdFactor) {
        this.rkxdFactor = rkxdFactor;
    }

    public void setZxxkcFactor(EduCodeFactor zxxkcFactor) {
        this.zxxkcFactor = zxxkcFactor;
    }

    public void setPublisherFactor(EduCodeFactor publisherFactor) {
        this.publisherFactor = publisherFactor;
    }

    public void setNjFactor(EduCodeFactor njFactor) {
        this.njFactor = njFactor;
    }

    public void setFasciculeFactor(EduCodeFactor fasciculeFactor) {
        this.fasciculeFactor = fasciculeFactor;
    }

    public void setYearFactor(EduCodeFactor yearFactor) {
        this.yearFactor = yearFactor;
    }

    public void setEditionFactor(EduCodeFactor editionFactor) {
        this.editionFactor = editionFactor;
    }

    public void setBookFactor(EduCodeFactor bookFactor) {
        this.bookFactor = bookFactor;
    }

    public EduCodeFactor getChapterCascadeFactor() {
        return chapterCascadeFactor;
    }

    public void setChapterCascadeFactor(String chapterCascadeFactor) {
        this.chapterCascadeFactor.setCode(chapterCascadeFactor);
    }

    public EduCodeFactor getEduCodeFactor() {
        return eduCodeFactor;
    }

    public void setEduCodeFactor(String eduCodeFactor) {
        this.eduCodeFactor.setCode(eduCodeFactor);
    }
}
