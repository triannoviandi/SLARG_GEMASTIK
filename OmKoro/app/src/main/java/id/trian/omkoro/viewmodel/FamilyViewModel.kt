package id.trian.omkoro.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import id.trian.omkoro.service.model.User
import id.trian.omkoro.service.repository.FirebaseLoginRepository
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import id.trian.omkoro.service.model.RequestFamily
import kotlinx.coroutines.tasks.await


class FamilyViewModel: ViewModel() {

    val TAG = "FAMILY_VIEW_MODEL"
    var firebaseRepository = FirebaseLoginRepository()
    var uid = FirebaseAuth.getInstance().uid!!
    var requestUser : MutableLiveData<ArrayList<RequestFamily>>? = MutableLiveData()
    var listRequestProfile : MutableLiveData<ArrayList<User>> = MutableLiveData()
    var familyUid : MutableLiveData<ArrayList<String>>? = MutableLiveData()

    suspend fun addRequestFamily(nip: String) : Boolean{
        var exist : Boolean
        exist = false
        firebaseRepository.checkUidByNip(nip).addOnSuccessListener {
            if (!it.isEmpty){
                exist = true
                it.forEach{selecteduser ->
                    if (selecteduser.id != uid){
                        firebaseRepository.addFamily(selecteduser.id)
                    }
                }
            } else {
                exist = false
            }
        }.await()
        return exist

    }

    fun getFamilyCollection(): MutableLiveData<ArrayList<String>>?{
        firebaseRepository.getFamilyList().addOnSuccessListener {
            var dummy : ArrayList<String>  = ArrayList()
            if (!it.isEmpty){
                it.forEach {document ->
                    dummy.add(document.id)
                }
                familyUid!!.value = dummy
                return@addOnSuccessListener
            } else {
                var dummy : ArrayList<String>  = ArrayList()
                dummy.add("thisisnull")
                familyUid!!.value = dummy
                return@addOnSuccessListener
            }

        }
        return familyUid
    }

    fun getRequestCollection(): MutableLiveData<ArrayList<RequestFamily>>? {
        firebaseRepository.getRequestList().addOnSuccessListener {
            if (!it.isEmpty){
                val types = it.toObjects(RequestFamily::class.java)
                var dummy : ArrayList<RequestFamily>  = ArrayList()
                dummy.addAll(types)
                requestUser!!.value = dummy
                return@addOnSuccessListener
            } else{
                var dummy : ArrayList<RequestFamily>  = ArrayList()
                dummy.add(RequestFamily("thisisnull", false))
                requestUser!!.value = dummy
                return@addOnSuccessListener
            }
        }.addOnFailureListener {
            return@addOnFailureListener
        }
        return requestUser
    }


    fun getRequestProfile(listUid : ArrayList<RequestFamily>): MutableLiveData<ArrayList<User>>{

        var dummy : ArrayList<User>  = ArrayList()
        firebaseRepository.getAllProfile().addOnSuccessListener {
            if (!it.isEmpty){
                val types = it.toObjects(User::class.java)
                var dummySelected : ArrayList<User>  = ArrayList()
                dummy.addAll(types)
                dummy.forEach {family ->
                    for (uidSelected in listUid){
                        if (uidSelected.uid == family.uid){
                            dummySelected.add(family)
                        }
                    }
                }
                Log.d("wewewewe", "HOW" + dummy.size)
            }
        }

//        listUid.forEach {
//            Log.d("wewew", "2" + it.toString())
//            firebaseRepository.getRequestList_profile(it.uid).addOnSuccessListener {select ->
//
//                if (select.toObject(User::class.java) != null){
//                    Log.d("wewew", "3" + select.toString())
//                    val types = select.toObject(User::class.java)
//                    dummy.add(types!!)
//                    Log.d("wewew", "4" + dummy.toString())
//                }
//            }
//        }
        listRequestProfile.value = dummy
        Log.d("wewew", "5" + listRequestProfile.value.toString())
        return listRequestProfile
    }

    fun acceptRequestFamily(uid : String){
        firebaseRepository.acceptFamilyRequest(uid).addOnSuccessListener {
            Log.d("firebasetracker", "success add to family list")
            firebaseRepository.deleteFamilyRequest(uid).addOnSuccessListener {
                Log.d("firebasetracker", "success to delete request")
            }
        }
    }

    fun deleteRequestFamily(uid: String){
        firebaseRepository.deleteFamilyRequest(uid).addOnSuccessListener {
            Log.d("firebasetracker", "success to delete request")
        }
    }
}