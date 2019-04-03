package tech.laihz.kcalculator

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var nowOp=0     //0null 1+ 2- 3* 4/
    private var numA=0.0
    private var numB=0.0
    private var Switcher=0 //0A 1B
    private var OpPressed=false
    private var FloatBtnPress=false
    private var DotCount=0

    private var EastEgg=0 //max=10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            var result=ExecAll(numA,numB,nowOp)
            var Iresult=0
            if((result-result.toInt())==0.0){
                Iresult=result.toInt()
                textViewResult.text=Iresult.toString()
            }else textViewResult.text=result.toString()
            this.textViewMain.text=""
            this.textViewOp.text=""
            OpPressed=false
            Switcher=0
            numA=0.0
            numB=0.0
            FloatBtnPress=true
            DotCount=0
            Snackbar.make(view, result.toString(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)


    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.AboutBtn -> {
                startActivity(Intent(this,AboutActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.


        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun GetDigit(view:View){
        if(FloatBtnPress)textViewResult.text=""
        if(Switcher==0){
            this.textViewMain.append((view as Button).text)
            numA=textViewMain.text.toString().toDouble()
        }else{
            this.textViewResult.append((view as Button).text)
            numB=this.textViewResult.text.toString().toDouble()
        }
    }

    fun GetDot(view:View){
        if(Switcher==0){
            DotCount++
            if(DotCount==1) this.textViewMain.append((view as Button).text)
            numA=textViewMain.text.toString().toDouble()
        }else{
            DotCount++
            if(DotCount==1) this.textViewResult.append((view as Button).text)
            numB=this.textViewResult.text.toString().toDouble()
        }
    }



    fun ClearBtn(view:View){
        this.textViewMain.text=""
        this.textViewResult.text=""
        this.textViewOp.text=""
        OpPressed=false
        Switcher=0
        numA=0.0
        numB=0.0
        OpPressed=false
        FloatBtnPress=false
        DotCount=0
        EastEgg++
        when(EastEgg){
            15->Snackbar.make(view, "666666", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            20->Snackbar.make(view, "不要再按了", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            25->Snackbar.make(view, "真没了", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            30->Snackbar.make(view, "Boy Next Door", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            50->Snackbar.make(view, "你是恶魔？", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            100->Snackbar.make(view, "疯狂（x_x）", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            200->Toast.makeText(this,"解锁成就：大师",Toast.LENGTH_SHORT).show()
            250->Snackbar.make(view, "兄弟，250了，再按给你重置了", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            270->Snackbar.make(view, "已重置", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            271->EastEgg=0
        }
    }

    fun GetOp(view:View){
        if(FloatBtnPress){}else {
            val Op = (view as Button).id
            if (OpPressed) {
                var result = ExecAll(numA, numB, nowOp)
                var Iresult = 0
                if ((result - result.toInt()) == 0.0) {
                    Iresult = result.toInt()
                    textViewResult.text = Iresult.toString()
                } else textViewMain.text = result.toString()
                numA = result
                numB = 0.0
                textViewResult.text = ""
                textViewOp.text = ""
                DotCount = 0
            }
            when (Op) {
                R.id.btn_plus -> {
                    nowOp = 1
                    this.textViewOp.text = "+"
                }
                R.id.btn_sub -> {
                    nowOp = 2
                    this.textViewOp.text = "-"
                }
                R.id.btn_multi -> {
                    nowOp = 3
                    this.textViewOp.text = "*"
                }
                R.id.btn_div -> {
                    nowOp = 4
                    this.textViewOp.text = "/"
                }
            }

            OpPressed = true
            Switcher = 1
            DotCount = 0
        }
    }



    fun ExecAll(numA:Double,numB:Double,nowOp:Int):Double{
        var temp=0.0
        when(nowOp){
            1->temp=numA+numB
            2->temp=numA-numB
            3->temp=numA*numB
            4->{
                if(numB==0.0){
                    Toast.makeText(this,"除数不能为0",Toast.LENGTH_SHORT).show()
                }else{
                    temp=numA/numB
                }
            }
        }
        return temp
    }

}
