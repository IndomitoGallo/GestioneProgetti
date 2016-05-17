/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma2.gestioneprogetti.response;

import java.util.List;

/**
 *
 * @author F.Camerlengo
 */
public class PMsEmployeesRES extends AbstractRES{

    private List<UserRES> usersList;

    public List<UserRES> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<UserRES> usersList) {
        this.usersList = usersList;
    }
}
