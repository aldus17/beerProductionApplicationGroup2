package com.mycompany.domain.chooseuser;

import com.mycompany.crossCutting.objects.Machine;
import com.mycompany.data.dataAccess.ChooseMachineDataHandler;
import com.mycompany.data.interfaces.IChooseMachineDataHandler;
import com.mycompany.domain.chooseuser.interfaces.IChooseUser;
import java.util.List;

public class ChooseUser implements IChooseUser {

    @Override
    public List<Machine> getMachineList() {
        IChooseMachineDataHandler dh = new ChooseMachineDataHandler();
        List<Machine> machineList = dh.getMachineList();
        return machineList;
    }

}
