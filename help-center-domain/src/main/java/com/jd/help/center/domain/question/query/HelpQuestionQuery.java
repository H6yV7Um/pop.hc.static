package com.jd.help.center.domain.question.query;

import com.jd.help.center.domain.category.query.HelpCategoryQuery;
import com.jd.help.center.domain.question.HelpQuestion;

/**
 * Created by gaofei on 14-3-5
 */
public class HelpQuestionQuery{

    /**
     *  0:未选择标签
     *  1:up标签
     *  2:new标签
     *  3:hot标签
     */
    private int logoType;
    private static String  LOGO_TYPE ="logoType";

    public HelpQuestion helpQuestion;

    public HelpQuestionQuery(){}

    public HelpQuestionQuery(HelpQuestion helpQuestion){
        this.helpQuestion = helpQuestion;
        if(helpQuestion !=null) convertFeatures(helpQuestion);
    }

    public int getLogoType() {
        return logoType;
    }

    public void setLogoType(int logoType) {
        this.logoType = logoType;
    }

    public void setHelpQuestion(HelpQuestion helpQuestion) {this.helpQuestion = helpQuestion;}

    public HelpQuestion getHelpQuestion(){
        return helpQuestion;
    }



    /**
     * faatures to  Property
     */
    private void convertFeatures (HelpQuestion helpQuestion){
         if(helpQuestion != null && helpQuestion.getFeatures() != null && !"".equals(helpQuestion.getFeatures())){
             String featureGroup[] = helpQuestion.getFeatures().split("\\^");
             for (int i = 0; i < featureGroup.length; i++) {
                 String s = featureGroup[i];
                 String[] curFeature = s.split(":");
                 for (int j = 0; j < curFeature.length; j++) {
                     String s1 = curFeature[j];
                     //feature 转 logoType
                     if (LOGO_TYPE.equals(s1)) {
                         this.setLogoType(Integer.parseInt(curFeature[j + 1]));
                         break;
                     }
                 }
             }
         }
    }

    /**
     * Property to features
     */
    public HelpQuestion convertProperty(HelpQuestion helpQuestion){
        StringBuffer sbf=new StringBuffer();
        if(this.getLogoType() > 0){
            sbf.append(LOGO_TYPE).append(":").append(this.getLogoType()).append("^");
        }
        if(sbf.length() > 0){
            helpQuestion.setFeatures(sbf.toString().substring(0, sbf.length() - 1));
        }else{
            helpQuestion.setFeatures(null);
        }
        return helpQuestion;
    }



}
