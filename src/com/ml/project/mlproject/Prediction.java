package com.ml.project.mlproject;

public class Prediction {
  private String id;
  private Double classLabel;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Double getClassLabel() {
    return classLabel;
  }

  public void setClassLabel(Double classLabel) {
    this.classLabel = classLabel;
  }

  
  public String[] arr() {
    String[] result = new String[2];
    result[0] = this.id;
    result[1] = this.classLabel.toString();
    return result;
  }
}
