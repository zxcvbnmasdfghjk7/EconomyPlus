package io.github.eddiediamondfire.economyplus.dependencies;

public interface Dependency {

    String getName();
    void onLoad();
    void onUnload();


}
