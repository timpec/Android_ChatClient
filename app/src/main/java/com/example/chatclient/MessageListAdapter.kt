package com.example.chatclient

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.chatclient.R.layout.messagelist_cell
import kotlinx.android.synthetic.main.messagelist_cell.view.*

class MyCustomAdapter(context: Context, private var items: ArrayList<UserData>): BaseAdapter(){

    private class ViewHolder(row: View?) {
        var txtName: TextView? = null
        var txtComment: TextView? = null

        init {
            this.txtName = row?.findViewById<TextView>(R.id.messageText)
            this.txtComment = row?.findViewById<TextView>(R.id.nameandtimeText)
        }
    }

    val mContext: Context
    init {
        mContext = context
    }

    override fun getCount(): Int {
        return items.size
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: LinearLayout
        val v: ViewHolder

        if(convertView == null){
            val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(messagelist_cell, null) as LinearLayout
            v = ViewHolder(view)
            view?.tag = v
        }else{
            view = convertView as LinearLayout
        }

        var userData = items[position]
        view.messageText.text = userData.name
        view.nameandtimeText.text = userData.time

        return view


       /* val textView = TextView(mContext)
        messageList.add(message)
        textView.setText(message)
        return textView*/
    }

    override fun getItem(position: Int): UserData {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


}