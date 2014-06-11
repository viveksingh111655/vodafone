package atg.siebel.configurator.ui;

import java.util.List;

public class UINodeWrapper {

  // -------------------------------------
  // property: cssFilePath
  private String mCssFilePath = null;

  public String getCssFilePath() {
    return mCssFilePath;
  }

  public void setCssFilePath(String pCssFilePath) {
    mCssFilePath = pCssFilePath;
  }

  // -------------------------------------
  // property: uiNodeList
  private List<UINode> mUiNodeList;

  public List<UINode> getUiNodeList() {
    return mUiNodeList;
  }

  public void setUiNodeList(List<UINode> pUiNodeList) {
    mUiNodeList = pUiNodeList;
  }

}
