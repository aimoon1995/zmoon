package com.project_study.my.common.utils;

public interface ITreeItem {

	public String getTreeId();
	public String getTreeParentId();
	public String getTreeName();
	public int getTreeOrder();
	public boolean disabled();
}
