//package com.project_study.my.common.utils;
//
//import com.ithinkdt.web.common.entity.ModuleEntity;
//
//import java.util.*;
//
///**
// * 树形表示Util
// */
//@SuppressWarnings({ "rawtypes", "unchecked" })
//public class TreeUtil {
//
//	public static interface ITreeItemMapper<E>{
//		public ITreeItem getTreeItem(E obj);
//	}
//
//	public static class DefaultTreeItemMapper<E> implements ITreeItemMapper<ITreeItem>{
//
//		@Override
//		public ITreeItem getTreeItem(
//				ITreeItem obj) {
//			return obj;
//		}
//
//	}
//
//	public static <T> List<TreeItem> getStatusTree(List<T> checkedItems, List<T> allItems, String rootNode, ITreeItemMapper<T> mapper) {
//		List<TreeItem> treeData = trsAccessToTreeItem(checkedItems, allItems, mapper);
//		TreeItem root = new TreeItem();
//		root.setId("0");
//		root.setText(rootNode);
//		if (treeData != null && treeData.size() != 0) {
//			List<TreeItem> list = new ArrayList<TreeItem>();
//			List<TreeItem> children = getChildrenByParent(root, treeData);
//			Collections.sort(children);
//			list.addAll(children);
//			root.setChildren(children);
//
//			if (list != null) {
//				for (int i = 0; i < list.size(); i++) {
//					List<TreeItem> subchildren = getChildrenByParent(list.get(i), treeData);
//					Collections.sort(subchildren);
//					if (subchildren != null) {
//						list.addAll(subchildren);
//					}
//					list.get(i).setChildren(subchildren);
//					if(subchildren.size() > 0){
//						list.get(i).getState().selected = false;
//					}
//				}
//			}
//		}
//
//		if(StringUtil.isNotEmpty(rootNode)){
//			List<TreeItem> result = new ArrayList<TreeItem>();
//			result.add(root);
//			return result;
//		}
//		return root.getChildren();
//	}
//
//	/**
//	 * 获得角色权限树
//	 * @param checkedItems
//	 * @param allItems
//	 * @return
//	 */
//	public static <T extends ITreeItem>  List<TreeItem> getStatusTree(List<T> checkedItems, List<T> allItems, String rootNode) {
//		return getStatusTree(checkedItems, allItems, rootNode, null);
//	}
//
//	public static <T> List<TreeItem> getTree(List<T> allItems, ITreeItemMapper<T> mapper) {
//		return getTree(allItems, null, mapper, null);
//	}
//
//	public static <T> List<TreeItem> getTree(List<T> allItems, String rootNode, ITreeItemMapper<T> mapper, String rootId) {
//		List<TreeItem> treeData = trsAccessToTreeItem(allItems, mapper);
//		TreeItem root = new TreeItem();
//		root.setId(StringUtil.getOrElse(rootId, "0"));
//		root.setText(rootNode);
//		if (treeData != null && treeData.size() != 0) {
//			List<TreeItem> list = new ArrayList<TreeItem>();
//			List<TreeItem> children = getChildrenByParent(root, treeData);
//			Collections.sort(children);
//			list.addAll(children);
//			root.setChildren(children);
//
//			if (list != null) {
//				for (int i = 0; i < list.size(); i++) {
//					List<TreeItem> subchildren = getChildrenByParent(list.get(i), treeData);
//					Collections.sort(subchildren);
//					if (subchildren != null) {
//						list.addAll(subchildren);
//					}
//					list.get(i).setChildren(subchildren);
//					if(subchildren.size() > 0){
//						list.get(i).getState().selected = false;
//					}
//				}
//			}
//		}
//
//		if(StringUtil.isNotEmpty(rootNode)){
//			List<TreeItem> result = new ArrayList<TreeItem>();
//			result.add(root);
//			return result;
//		}
//		return root.getChildren();
//	}
//
//
//
//	public static <T> List<TreeItem> getStatusTree(Map checkedItems, List<T> allItems, String rootNode, ITreeItemMapper mapper) {
//		List<TreeItem> treeData = trsAccessToTreeItem(checkedItems, allItems, mapper);
//		TreeItem root = new TreeItem();
//		root.setId("0");
//		root.setText(rootNode);
//		if (treeData != null && treeData.size() != 0) {
//			List<TreeItem> list = new ArrayList<TreeItem>();
//			List<TreeItem> children = getChildrenByParent(root, treeData);
//			Collections.sort(children);
//			list.addAll(children);
//			root.setChildren(children);
//
//			if (list != null) {
//				for (int i = 0; i < list.size(); i++) {
//					List<TreeItem> subchildren = getChildrenByParent(list.get(i), treeData);
//					Collections.sort(subchildren);
//					if (subchildren != null) {
//						list.addAll(subchildren);
//					}
//					list.get(i).setChildren(subchildren);
//					if(subchildren.size() > 0){
//						list.get(i).getState().selected = false;
//					}
//				}
//			}
//		}
//
//		if(StringUtil.isNotEmpty(rootNode)){
//			List<TreeItem> result = new ArrayList<TreeItem>();
//			result.add(root);
//			return result;
//		}
//		return root.getChildren();
//	}
//
//	public static List<TreeItem> getStatusTree(Map checkedItems, List<? extends ITreeItem> allItems, String rootNode) {
//		return getStatusTree(checkedItems, allItems, rootNode, new DefaultTreeItemMapper());
//	}
//
//	public static <T extends ITreeItem> List<TreeItem> getStatusTree(List<T> checkedItems, List<T> allItems) {
//		return getStatusTree(checkedItems, allItems, null, new DefaultTreeItemMapper());
//	}
//
//	public static <T> List<TreeItem> getStatusTree(List<T> checkedItems, List<T> allItems, ITreeItemMapper mapper) {
//		return getStatusTree(checkedItems, allItems, null, mapper);
//	}
//
//	public static <T extends ITreeItem> List<TreeItem> getStatusTree(Map checkedItems, List<T> allItems) {
//		return getStatusTree(checkedItems, allItems, null);
//	}
//
//	public static <T extends ITreeItem> List<TreeItem> getTree(List<T> allItems, String rootNode, String rootId) {
//		return getTree(allItems, rootNode, new DefaultTreeItemMapper(), rootId);
//	}
//
//	public static <T extends ITreeItem> List<TreeItem> getTree(List<T> allItems, String rootNode) {
//		return getTree(allItems, rootNode, new DefaultTreeItemMapper(), null);
//	}
//
//	public static <T extends ITreeItem> List<TreeItem> getTree(List<T> allItems) {
//		return getTree(allItems, "");
//	}
//
//	private static List<TreeItem> getChildrenByParent(TreeItem node, List<TreeItem> treeData) {
//		List<TreeItem> list = new ArrayList<TreeItem>();
//		for (int i = 0; i < treeData.size(); i++) {
//			TreeItem item = (TreeItem)treeData.get(i);
//			String spId = StringUtil.getOrElse(item.getParentId(), "0");
//			if (spId.equals(node.getId())) {
//				list.add(item);
//			}
//		}
//		return list;
//	}
//
//	private static <T> List<TreeItem> trsAccessToTreeItem(List<T> checkedItems, List<T> allItems, ITreeItemMapper<T> mapper) {
//		List<TreeItem> list = new ArrayList<TreeItem>();
//		for (int i = 0; i < allItems.size(); i++) {
//			boolean ischecked = false;
//			ITreeItem item = mapper.getTreeItem(allItems.get(i));
//			if(checkedItems != null){
//				for (int j = 0; j < checkedItems.size(); j++) {
//					if (item.getTreeId().equals(mapper.getTreeItem(checkedItems.get(j)).getTreeId())) {
//						ischecked = true;
//						break;
//					}
//				}
//			}
//			TreeItem node = new TreeItem();
//			node.setId(item.getTreeId());
//			node.setText(item.getTreeName());
//			node.getState().disabled = item.disabled();
//			node.getState().selected = ischecked;
//
//			if(item instanceof ModuleEntity){
//				ModuleEntity me = (ModuleEntity)item;
////				if( StringUtil.isEmpty(me.getPreActions()) && StringUtil.isEmpty(me.getAction())){
////					node.getState().selected = false;
////				}
//				node.setIcon(me.getCss());
//				node.setKbn(me.getModuleKbn());
//			}
//			node.setOrder(item.getTreeOrder());
//			node.setParentId(item.getTreeParentId());
//			node.setData(item);
//			list.add(node);
//		}
//		return list.size() > 0 ? list : null;
//	}
//
//
//	private static <T> List<TreeItem> trsAccessToTreeItem(Map<String, T> checkedItems, List<T> allItems, ITreeItemMapper mapper) {
//		List<TreeItem> list = new ArrayList<TreeItem>();
//		for (int i = 0; i < allItems.size(); i++) {
//			boolean ischecked = false;
//			ITreeItem item = mapper.getTreeItem(allItems.get(i));
//			if(checkedItems != null){
//				ischecked = checkedItems.containsKey(item.getTreeId());
//			}
//			TreeItem node = new TreeItem();
//			node.setId(item.getTreeId());
//			node.setText(item.getTreeName());
//			node.getState().selected = ischecked;
//			node.getState().disabled = item.disabled();
//			if(item instanceof ModuleEntity){
//				ModuleEntity me = (ModuleEntity)item;
////				if( StringUtil.isEmpty(me.getPreActions()) && StringUtil.isEmpty(me.getAction())){
////					node.getState().selected = false;
////				}
//				node.setIcon(me.getCss());
//				node.setKbn(me.getModuleKbn());
//			}
//			node.setOrder(item.getTreeOrder());
//			node.setParentId(item.getTreeParentId());
//			node.setData(item);
//			list.add(node);
//		}
//		return list.size() > 0 ? list : null;
//	}
//
//	private static <T> List<TreeItem> trsAccessToTreeItem(List<T> allItems, ITreeItemMapper mapper) {
//		List<TreeItem> list = new ArrayList<TreeItem>();
//		for (int i = 0; i < allItems.size(); i++) {
//			boolean ischecked = false;
//			ITreeItem item = mapper.getTreeItem(allItems.get(i));
//			TreeItem node = new TreeItem();
//			node.setId(item.getTreeId());
//			node.setText(item.getTreeName());
//			node.getState().selected = ischecked;
//			node.getState().disabled = item.disabled();
//			if(item instanceof ModuleEntity){
//				ModuleEntity me = (ModuleEntity)item;
////				if( StringUtil.isEmpty(me.getPreActions()) && StringUtil.isEmpty(me.getAction())){
////					node.getState().selected = false;
////				}
//				node.setIcon(me.getCss());
//				node.setKbn(me.getModuleKbn());
//			}
//			node.setOrder(item.getTreeOrder());
//			node.setParentId(item.getTreeParentId());
//			node.setData(item);
//			list.add(node);
//		}
//		return list.size() > 0 ? list : null;
//	}
//	public static List<TreeItem> groupByKbn(List<TreeItem> com.project_study.my.ocr.items, String kbnTextKey, boolean disabled, String ... forceKbns){
//		List<TreeItem> result = new ArrayList<TreeItem>();
//		Map<String, List<TreeItem>> map = new HashMap<String, List<TreeItem>>();
//		if(forceKbns != null){
//			for(String forceKbn : forceKbns){
//				map.put(forceKbn, new ArrayList<TreeItem>());
//			}
//		}
//
//		for(TreeItem item:com.project_study.my.ocr.items){
//			if(StringUtil.isNotEmpty(item.getKbn())){
//				if(!map.containsKey(item.getKbn())){
//					map.put(item.getKbn(), new ArrayList<TreeItem>());
//				}
//				map.get(item.getKbn()).add(item);
//			}else{
//				result.add(item);
//			}
//		}
//		for(String kbn : map.keySet()){
//			TreeItem item = new TreeItem();
//			item.setId(kbn);
//			item.getState().disabled = disabled;
//			// CodeFactory 已经废弃 CodeFactory.get(kbnTextKey,kbn) 2018-10-18 20:53:58
//			item.setText(DictUtil.getDictChValue(kbnTextKey, kbn));
//			List<TreeItem> children = map.get(kbn);
//			item.setChildren(children);
//			result.add(item);
//		}
//		return result;
//	}
//
//	public static List<TreeItem> groupByKbn(List<TreeItem> com.project_study.my.ocr.items, String kbnTextKey, String ... forceKbns){
//		return groupByKbn(com.project_study.my.ocr.items, kbnTextKey, false, forceKbns);
//	}
//
//	public static boolean disableTreeByLockFlag(List<TreeItem> com.project_study.my.ocr.items){
//		boolean lockFlag = false;
//		boolean hasLockChild = false;
//		for(TreeItem item : com.project_study.my.ocr.items){
//			if(item.getChildren() != null && item.getChildren().size() > 0){
//				hasLockChild = disableTreeByLockFlag(item.getChildren());
//			}
//			if(hasLockChild || item.getData() != null && item.getData() instanceof ModuleEntity && "1".equals(((ModuleEntity) item.getData()).getLockFlag())){
//				item.getState().disabled = true;
//				lockFlag = true;
//			}
//		}
//		return lockFlag;
//	}
//}
