package com.glenn.action;

public interface NewsAction {

    public boolean isNeedDelete();

    public void deleteDueNews();

    public void deleteBatchNews();
}
