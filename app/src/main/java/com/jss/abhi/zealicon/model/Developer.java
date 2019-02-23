package com.jss.abhi.zealicon.model;

/**
 * Created by abhi on 16/2/18.
 */

/**
 * THis model is used for all Developers Team and Sponsors
 */
public class Developer {

  private String name;
  private String imgurl;
  private String position;
  /*********This field is contact no for team and github url for developer************/
  private String gitUrl;

  //Constructor for Developer and Team members
  public Developer(String name, String imgurl, String position, String gitUrl) {
    this.name = name;
    this.imgurl = imgurl;
    this.position = position;
    this.gitUrl = gitUrl;
  }

  //Constructor for sponsors
  public Developer(String sponsorName,String giturl,String category){
    this.name = sponsorName;
    this.gitUrl = giturl;
    this.position = category;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImgurl() {
    return imgurl;
  }

  public void setGitUrl(String giturl) {
    this.gitUrl = gitUrl;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public String getMobNum() {
    return gitUrl;
  }

  public String getGitUrl() {
    return gitUrl;
  }




}
