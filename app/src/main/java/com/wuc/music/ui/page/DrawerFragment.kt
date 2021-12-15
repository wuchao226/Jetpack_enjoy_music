package com.wuc.music.ui.page

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.wuc.architecture.ui.adapter.SimpleBaseBindingAdapter
import com.wuc.music.R
import com.wuc.music.bridge.data.bean.LibraryInfo
import com.wuc.music.bridge.request.InfoRequestViewModel
import com.wuc.music.bridge.state.DrawerViewModel
import com.wuc.music.databinding.AdapterLibraryBinding
import com.wuc.music.databinding.FragmentDrawerBinding
import com.wuc.music.ui.base.BaseFragment

/**
 * @author : wuchao5
 * @date : 2021/12/3 14:47
 * @desciption : 抽屉的 左侧半界面
 */
class DrawerFragment : BaseFragment() {
    private var mBinding: FragmentDrawerBinding? = null

    private var mDrawerViewModel: DrawerViewModel? = null
    private var mInfoRequestViewModel: InfoRequestViewModel? = null
    private var mAdapter: SimpleBaseBindingAdapter<LibraryInfo?, AdapterLibraryBinding?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDrawerViewModel = getFragmentViewModelProvider(this).get(DrawerViewModel::class.java)
        mInfoRequestViewModel = getFragmentViewModelProvider(this).get(InfoRequestViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_drawer, container, false)
        mBinding = FragmentDrawerBinding.bind(view)
        mBinding?.vm = mDrawerViewModel
        mBinding?.click = ClickProxy()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = object : SimpleBaseBindingAdapter<LibraryInfo?, AdapterLibraryBinding?>(
            context,
            R.layout.adapter_library
        ) {

            override fun onSimpleBindItem(
                binding: AdapterLibraryBinding?,
                item: LibraryInfo?,
                holder: RecyclerView.ViewHolder?
            ) {
                // 把数据 设置好，就显示数据了
                binding?.info = item
                binding?.root?.setOnClickListener {
                    Toast.makeText(mContext, "哎呀，还在研发中，猴急啥?...", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 设置适配器 到 RecyclerView
        mBinding!!.rv.adapter = mAdapter

        // 观察者 监听 眼睛 数据发送改变，UI就马上刷新
        // 观察这个数据的变化，如果 libraryLiveData 变化了，我就要要变，我就要更新到 RecyclerView
        mInfoRequestViewModel!!.libraryLiveData?.observe(viewLifecycleOwner, { libraryInfos ->

            // 以前是 间接的通过 状态VM 修改
            // mDrawerViewModel.xxx.setValue = libraryInfos

            // 这里特殊：直接更新UI，越快越好
            mAdapter?.list = libraryInfos
            mAdapter?.notifyDataSetChanged()
        })

        // 请求数据 调用 Request 的 ViewModel 加载数据
        mInfoRequestViewModel!!.requestLibraryInfo()
    }

    inner class ClickProxy {
        fun logoClick() {
            showShortToast("click")
        }
    }
}