package com.jd.help.center.domain.dispatch.xmlbean;

import java.io.Serializable;
import java.util.List;

/**
 * 夜间配 xmlbean
 * <br>author: chenbaogang@jd.com
 * <br>DateTime: May 14, 2013 9:39:19 AM
 * <br>Version 1.0
 */
public class NightShipArea extends DispatchEntity implements Serializable {

	/**
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 1169585255613740368L;

	/**
	 * 区域列表
	 */
	private List<Integer> areas;

	/**
	 * 城镇列表
	 */
	private List<Integer> towns;

	private String messageOnBefore;

	private String messageOnOverdue;

	private String timeSpanBySubmitStr;

	private String timeSpanByTransferStr;

	private boolean isUseNightShip;

	private String nightShipMessage;

	public List<Integer> getAreas() {
		return areas;
	}

	public void setAreas(List<Integer> areas) {
		this.areas = areas;
	}

	public List<Integer> getTowns() {
		return towns;
	}

	public void setTowns(List<Integer> towns) {
		this.towns = towns;
	}

	public String getMessageOnBefore() {
		return messageOnBefore;
	}

	public void setMessageOnBefore(String messageOnBefore) {
		this.messageOnBefore = messageOnBefore;
	}

	public String getMessageOnOverdue() {
		return messageOnOverdue;
	}

	public void setMessageOnOverdue(String messageOnOverdue) {
		this.messageOnOverdue = messageOnOverdue;
	}

	public String getTimeSpanBySubmitStr() {
		return timeSpanBySubmitStr;
	}

	public void setTimeSpanBySubmitStr(String timeSpanBySubmitStr) {
		this.timeSpanBySubmitStr = timeSpanBySubmitStr;
	}

	public String getTimeSpanByTransferStr() {
		return timeSpanByTransferStr;
	}

	public void setTimeSpanByTransferStr(String timeSpanByTransferStr) {
		this.timeSpanByTransferStr = timeSpanByTransferStr;
	}

	public boolean isUseNightShip() {
		return isUseNightShip;
	}

	public void setUseNightShip(boolean isUseNightShip) {
		this.isUseNightShip = isUseNightShip;
	}

	public String getNightShipMessage() {
		return nightShipMessage;
	}

	public void setNightShipMessage(String nightShipMessage) {
		this.nightShipMessage = nightShipMessage;
	}

}
