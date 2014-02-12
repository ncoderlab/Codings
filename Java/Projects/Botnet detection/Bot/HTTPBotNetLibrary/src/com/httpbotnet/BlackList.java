/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.httpbotnet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Student
 */
public class BlackList implements Serializable {
    private List<String> blackList = new ArrayList<String>();

    public List<String> getBlackList() {
        return blackList;
    }

    public void setBlackList(List<String> blackList) {
        this.blackList = blackList;
    }

    public void addBlackList(String blackIP) {
        this.blackList.add(blackIP);
    }
}
