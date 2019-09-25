package com.zzx.custom.payinputview

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.password_key_borad.view.*

class PasswordKeyboardView : FrameLayout {
    private var passwordView: PasswordView? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attributeSet,
            defStyleAttr
    ) {
        init()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.password_key_borad, this)
        recycler_view.layoutManager = GridLayoutManager(context, 3)
        recycler_view.adapter = PasswordKeyboardAdapter().apply {
            onPasswordClickListener = object : PasswordKeyboardAdapter.OnPasswordClickListener {
                override fun onPasswordClick(position: Int) {
                    when {
                        position <= 8 -> passwordView?.updatePassword(position + 1)
                        position == 10 -> passwordView?.updatePassword(0)
                        position == 11 -> passwordView?.updatePassword(-1)
                    }
                }
            }
        }
    }

    fun bindPasswordView(passwordView: PasswordView) {
        this.passwordView = passwordView
    }
}

class PasswordKeyboardAdapter : RecyclerView.Adapter<PasswordKeyboardAdapter.ViewHolder>() {
    var onPasswordClickListener: OnPasswordClickListener? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_password_key_board, p0, false)
        return ViewHolder(view, this)
    }

    override fun getItemCount(): Int {
        return 12
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindData(position)
    }

    class ViewHolder(override val containerView: View, private val adapter: PasswordKeyboardAdapter) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        private var tvPassword = itemView.findViewById<TextView>(R.id.tv_password)
        private var ivDelete = itemView.findViewById<ImageView>(R.id.iv_delete)
        private var lineTop = itemView.findViewById<View>(R.id.line_top)
        private var lineRight = itemView.findViewById<View>(R.id.line_right)

        init {
            itemView.setOnClickListener {
                adapter.onPasswordClickListener?.onPasswordClick(adapterPosition)
            }
        }

        fun bindData(position: Int) {
            when {
                position <= 8 -> {
                    tvPassword.visibility = View.VISIBLE
                    ivDelete.visibility = View.GONE
                    tvPassword.text = (position + 1).toString()
                }
                position == 10 -> {
                    tvPassword.visibility = View.VISIBLE
                    ivDelete.visibility = View.GONE
                    tvPassword.text = 0.toString()
                }
                position == 11 -> {
                    tvPassword.visibility = View.GONE
                    ivDelete.visibility = View.VISIBLE
                }
                else -> {
                    tvPassword.visibility = View.GONE
                    ivDelete.visibility = View.GONE
                }
            }

            if (position < 3 || position == 9 || position == 11) {
                lineTop.visibility = View.GONE
            } else {
                lineTop.visibility = View.VISIBLE
            }
            if (position % 3 == 2 || position > 8) {
                lineRight.visibility = View.GONE
            } else {
                lineRight.visibility = View.VISIBLE
            }
        }
    }

    interface OnPasswordClickListener {
        fun onPasswordClick(position: Int)
    }
}