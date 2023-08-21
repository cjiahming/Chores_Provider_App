package tarc.edu.my.myapplication

import androidx.lifecycle.ViewModel
import tarc.edu.my.myapplication.ChoreData.chores

class ChoreViewModel :ViewModel(){
    private var searchData: chores = chores(null,null,null,null,null,null,null,null,false,null);

    fun getData():chores{
        return searchData;
    }

    fun setData(chores1 : chores){
        searchData = chores1
    }
}