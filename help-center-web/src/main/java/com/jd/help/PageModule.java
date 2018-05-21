package com.jd.help;

/**
 * @author laichendong
 * @since 2014/12/27 14:52
 */
public enum PageModule {
    INDEX(new String[]{"index_hotIssue", "index_hotService", "index_catalogList","index_banner","index_studyArea","index_venderSpecial","index_venderServerNavigation"}, new String[]{"��ҳ-�ȵ�����", "��ҳ-������������", "��ҳ-��Ŀ�б�","��ҳ-�ֲ�ͼ","��ҳ-ѧϰר��","�̼�ר��","��ҳ-�̼ҷ��񵼺�"}) //
    , ISSUE(new String[]{"issue_hotIssue", "issue_quickNav"}, new String[]{"����������ҳ-�ȵ�����", "����������ҳ-���ٵ���"}) //
    , SERVICE(new String[]{"service_allService", "service_hotService"}, new String[]{"����������ҳ-ȫ����������", "����������ҳ-������������"}) //
    , CUSTOM(new String[]{"custom_banner"}, new String[]{"��ϵ�ͷ�-Bannerͼ"}) //
    , GUIDE(new String[]{"guide_guide", "guide_imgGuide"}, new String[]{"����ָ��-���ֹ����ѧ", "����ָ��-ͼ��ָ��"}) //
    ;


    private String[] moduleKeies;
    private String[] moduleNames;

    PageModule(String[] moduleKeies, String[] moduleNames) {
        this.moduleKeies = moduleKeies;
        this.moduleNames = moduleNames;
    }

    public String[] getModuleKeies() {
        return moduleKeies;
    }

    public String[] getModuleNames() {
        return moduleNames;
    }
}
