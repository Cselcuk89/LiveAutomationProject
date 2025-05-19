package com.selcuk.FrameworkPages.root;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class RootPage {
    WebDriver driver;
    public RootPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }
    @FindBy(how = How.XPATH, using = "//div[@id='content']/h1")
    private WebElement pageHeading;
    @FindBy(how = How.XPATH, using = "//i[@class='fa fa-home']")
    private WebElement homeBreadCrumb;
    @FindBy(how = How.XPATH, using = "//ul[@class='breadcrumb']//a[text()='Account']")
    private WebElement accountBreadCrumb;
    @FindBy(how = How.XPATH, using = "//div[@class='alert alert-danger alert-dismissible']")
    private WebElement pageLevelWarning;
    @FindBy(how = How.XPATH, using = "//div[@class='alert alert-success alert-dismissible']")
    private WebElement pageLevelSuccessMessage;



}
