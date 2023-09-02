package com.example.serviceengineer

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.CloudQueue
import androidx.compose.runtime.mutableStateOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.serviceengineer.models.*
import com.example.serviceengineer.navigation.RootNavigationGraph
import com.example.serviceengineer.screens.helpers.*
import com.example.serviceengineer.ui.theme.ServiceEngineerTheme
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue


private lateinit var databaseClientsList: DatabaseReference
private lateinit var databasePriceList: DatabaseReference
private lateinit var databaseSparePartsList: DatabaseReference
private lateinit var databaseReportsList: DatabaseReference
private lateinit var databaseNotesList: DatabaseReference
private lateinit var databaseRequestsList: DatabaseReference
private lateinit var databaseUserData: DatabaseReference
private lateinit var databaseParameters: DatabaseReference
private lateinit var databaseCompany: DatabaseReference
private lateinit var databaseSchemes: DatabaseReference
private lateinit var databasePrograms: DatabaseReference
private lateinit var databaseEquipment: DatabaseReference
private lateinit var databaseInstructions: DatabaseReference
private lateinit var databaseApp: DatabaseReference

class MainActivity : ComponentActivity() {

    private val viewModel: SplashScreenClass by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val startMainScreen = mutableStateOf(false)

        cUser?.reload()
        if (cUser != null) {
            if (!cUser.isEmailVerified)
                cUser.delete()
            else
                databaseSync()
        }

        /*if(checkPlayServices(this))
            authIcon.value = Icons.Outlined.CloudQueue
        else
            authIcon.value = Icons.Outlined.CloudOff*/

        installSplashScreen().apply {
            setKeepOnScreenCondition{
                if (!viewModel.isLoading.value)
                    startMainScreen.value = true
                viewModel.isLoading.value
            }
        }

        setContent {
            ServiceEngineerTheme(darkTheme = false) {
                if (startMainScreen.value){
                    if (uType.value == "Сотрудник СЦ"){
                        getCompanyData()
                        if (uPost.value != "Администратор")
                            getCompanies()
                    }
                    RootNavigationGraph(navController = rememberNavController())
                }
            }
        }
    }
}

fun databaseSync(){
    databaseClientsList =
        FirebaseDatabase.getInstance().getReference("Users").child(mAuth.currentUser!!.uid)
            .child("Clients")
    databasePriceList =
        FirebaseDatabase.getInstance().getReference("Users").child(mAuth.currentUser!!.uid)
            .child("PriceList")
    databaseReportsList =
        FirebaseDatabase.getInstance().getReference("Users").child(mAuth.currentUser!!.uid)
            .child("Reports")
    databaseNotesList =
        FirebaseDatabase.getInstance().getReference("Users").child(mAuth.currentUser!!.uid)
            .child("Notes")
    databaseRequestsList =
        FirebaseDatabase.getInstance().getReference("Users").child(mAuth.currentUser!!.uid)
            .child("Requests")
    databaseUserData =
        FirebaseDatabase.getInstance().getReference("Users").child(mAuth.currentUser!!.uid)
            .child("UserData")
    databaseParameters =
        FirebaseDatabase.getInstance().getReference("Users").child(mAuth.currentUser!!.uid)
            .child("Parameters")
    databaseCompany =
        FirebaseDatabase.getInstance().getReference("Organizations")
    databaseSparePartsList =
        FirebaseDatabase.getInstance().getReference("Users").child(mAuth.currentUser!!.uid)
            .child("SpareParts")
    databaseSchemes =
        FirebaseDatabase.getInstance().getReference("Schemes")
    databasePrograms =
        FirebaseDatabase.getInstance().getReference("Programs")
    databaseEquipment =
        FirebaseDatabase.getInstance().getReference("Equipment")
    databaseInstructions =
        FirebaseDatabase.getInstance().getReference("Instructions")
    databaseApp =
        FirebaseDatabase.getInstance().getReference("App")
    getApp()
    getUserData()
    getClients()
    getPriceList()
    getSparePartsList()
    getReports()
    getNotes()
    getRequests()
    getParameters()
    getSchemes()
    getEquipment()
    getPrograms()
    getInstructions()
}

private fun getApp(){
    databaseApp.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val value = dataSnapshot.getValue<App>()
            if (value != null) {
                ServerVersion.value = value.version
                authIcon.value = Icons.Outlined.CloudQueue
            } else
                authIcon.value = Icons.Outlined.CloudOff
        }

        override fun onCancelled(databaseError: DatabaseError) {
            return
        }
    })
}

private fun getUserData(){
    databaseUserData.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val value = dataSnapshot.getValue<User>()
            if (value != null) {
                uKey.value = value.id
                uEmail.value = value.email
                uSurname.value = value.surname
                uName.value = value.name
                uPatronymic.value = value.patronymic
                uJob.value = value.job
                uPost.value = value.post
                uType.value = value.type
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            return
        }
    })
}

private fun getClients(){
    databaseClientsList.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            clientList.clear()
            for(clients in dataSnapshot.children) {
                val value = clients.getValue<Client>()
                if (value != null) {
                    clientList.add(value)
                }
            }
            clientList.sortBy { client -> client.clientSurname }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            return
        }
    })
}

private fun getPriceList(){
    databasePriceList.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            priceList.clear()
            for (pricelist in dataSnapshot.children) {
                val value = pricelist.getValue<Service>()
                if (value != null) {
                    priceList.add(value)
                }
            }
            priceList.sortBy { it.category }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            return
        }
    })
}

private fun getSparePartsList(){
    databaseSparePartsList.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            sparePartsList.clear()
            for (spareParts in dataSnapshot.children) {
                val value = spareParts.getValue<SparePart>()
                if (value != null) {
                    sparePartsList.add(value)
                }
            }
            sparePartsList.sortBy { it.category }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            return
        }
    })
}

private fun getReports(){
    databaseReportsList.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            reportsList.clear()
            for (report in dataSnapshot.children) {
                val value = report.getValue<Report>()
                if (value != null) {
                    reportsList.add(value)
                }
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            return
        }
    })
}

private fun getNotes(){
    databaseNotesList.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            noteList.clear()
            for (notes in dataSnapshot.children) {
                val value = notes.getValue<Note>()
                if (value != null) {
                    noteList.add(value)
                }
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            return
        }
    })
}

private fun getRequests(){
    databaseRequestsList.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            requestList.clear()
            for (notes in dataSnapshot.children) {
                val value = notes.getValue<Request>()
                if (value != null) {
                    requestList.add(value)
                }
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            return
        }
    })
}

private fun getParameters(){
    databaseParameters.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            parameterList.clear()
            for (params in dataSnapshot.children) {
                val value = params.getValue<Parameter>()
                if (value != null) {
                    parameterList.add(value)
                }
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            return
        }
    })
}

private fun getCompanyData(){
    databaseCompany.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            CompanyRequestList.clear()
            for (org in dataSnapshot.children) {
                if (org.key == uJob.value){
                    val data = org.child("Data").getValue<Company>()
                    if (data != null) {
                        CompanyName.value = data.name
                        CompanyMembersCount.value = data.membersCount
                    }
                    val path = org.child("Request")
                    for (request in path.children) {
                        val value = request.getValue<RequestToCompany>()
                        if (uPost.value == "Инженер (не подтверждено)"
                            && value != null && value.validation && value.userEmail == uEmail.value) {
                            val requestRef = path.ref.child(value.id)
                            requestRef.removeValue()
                            val path2 = databaseUserData.ref
                            val data2 = mapOf<String, Any>(
                                "post" to "Инженер"
                            )
                            path2.updateChildren(data2)
                        }
                        else {
                            if (value != null) {
                                CompanyRequestList.add(value)
                            }
                        }
                    }
                    break
                }
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            return
        }
    })
}

private fun getCompanies(){
    databaseCompany.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            CompanyList.clear()
            for (org in dataSnapshot.children) {
                val data = org.child("Data").getValue<Company>()
                if (data != null) {
                    CompanyList.add(data)
                }
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            return
        }
    })
}

private fun getSchemes(){
    databaseSchemes.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            schemeList.clear()
            for(schemes in dataSnapshot.children) {
                val value = schemes.getValue<Scheme>()
                if (value != null) {
                    schemeList.add(value)
                }
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            return
        }
    })
}

private fun getPrograms(){
    databasePrograms.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            programsList.clear()
            for(programs in dataSnapshot.children) {
                val value = programs.getValue<Paper>()
                if (value != null) {
                    programsList.add(value)
                }
            }
            programsList.sortBy { it.name }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            return
        }
    })
}

private fun getEquipment(){
    databaseEquipment.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            equipmentList.clear()
            for(equipment in dataSnapshot.children) {
                val value = equipment.getValue<Paper>()
                if (value != null) {
                    equipmentList.add(value)
                }
            }
            equipmentList.sortBy { it.name }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            return
        }
    })
}

private fun getInstructions(){
    databaseInstructions.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            instructionsList.clear()
            for(instruction in dataSnapshot.children) {
                val value = instruction.getValue<Paper>()
                if (value != null) {
                    instructionsList.add(value)
                }
            }
            instructionsList.sortBy { it.name }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            return
        }
    })
}