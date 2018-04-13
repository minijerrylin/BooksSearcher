package jpa.domain;


import jpa.domain.embeddable.OperationInfo;

import javax.persistence.*;

@Entity(name = "Book")
@Table(
        name = "Book",
        indexes = {
                @Index(name = "idx_book_isbn13", columnList = "isbn13", unique = true),
                @Index(name = "idx_book_isbn10", columnList = "isbn10", unique = true)
        }
)
public class Book {

  @Id
  @SequenceGenerator(name = "book_generator", sequenceName = "book_sequence", initialValue = 100)
  @GeneratedValue(generator = "book_generator")
  private Long id;

  @Column(length = 128, nullable = false)
  private String title;

  protected Book() {
  }

  public Book(String title) {
    this.title = title;
  }

  private String originalTile;

  @Column(length = 13)
  private String isbn13;

  @Column(length = 10)
  private String isbn10;

  private int pages;

  private String author;

  private String publisher;

  private long publishTime;

  private OperationInfo operationInfo;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getOriginalTile() {
    return originalTile;
  }

  public void setOriginalTile(String originalTile) {
    this.originalTile = originalTile;
  }

  public String getIsbn13() {
    return isbn13;
  }

  public void setIsbn13(String isbn13) {
    this.isbn13 = isbn13;
  }

  public String getIsbn10() {
    return isbn10;
  }

  public void setIsbn10(String isbn10) {
    this.isbn10 = isbn10;
  }

  public int getPages() {
    return pages;
  }

  public void setPages(int pages) {
    this.pages = pages;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public long getPublishTime() {
    return publishTime;
  }

  public void setPublishTime(long publishTime) {
    this.publishTime = publishTime;
  }

  public OperationInfo getOperationInfo() {
    return operationInfo;
  }

  public void setOperationInfo(OperationInfo operationInfo) {
    this.operationInfo = operationInfo;
  }
}