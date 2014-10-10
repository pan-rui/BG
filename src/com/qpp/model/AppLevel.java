package com.qpp.model;

import javax.validation.constraints.NotNull;

/**
 * Created by SZ_it123 on 2014/9/25.
 */
public class AppLevel extends AbstractAppKey {
    @NotNull
    private String levelName;
    private String levelDesc;

    @Override
    public AppKeyType getType() {
        return AppKeyType.APP_LEVEL;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLevelDesc() {
        return levelDesc;
    }

    public void setLevelDesc(String levelDesc) {
        this.levelDesc = levelDesc;
    }

}
