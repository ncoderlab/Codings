/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package httpbotnetclient.client;

import com.httpbotnet.BlackList;

/**
 *
 * @author Student
 */
public interface BlackListCallback {
    public void updateBlackList(BlackList blackList);
}
