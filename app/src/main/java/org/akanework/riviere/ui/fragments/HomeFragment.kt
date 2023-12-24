package org.akanework.riviere.ui.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.button.MaterialButton
import com.google.android.material.color.MaterialColors
import org.akanework.riviere.R
import org.akanework.riviere.ui.adapters.HomeChipAdapter
import org.akanework.riviere.ui.adapters.HomePresetAdapter
import org.akanework.riviere.ui.data.HolderTypes


class HomeFragment : BaseFragment() {

    // https://github.com/material-components/material-components-android/issues/1984#issuecomment-1089710991
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setBackgroundColor(
            MaterialColors.getColor(view, com.google.android.material.R.attr.colorSurfaceContainer)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("TAG", "HOMEFRAGMENT ONCREATE")
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        val bottomSheet: FrameLayout = rootView.findViewById(R.id.bottom_sheet)
        val formatSwitchButton: MaterialButton = rootView.findViewById(R.id.plus_minus_switch)
        val standardBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        val presetRecyclerView: RecyclerView = rootView.findViewById(R.id.preset_recyclerview)
        val chipRecyclerView: RecyclerView = rootView.findViewById(R.id.chips_recyclerview)

        val displayMetrics = DisplayMetrics()

        requireActivity().windowManager.getDefaultDisplay().getMetrics(displayMetrics)

        formatSwitchButton.backgroundTintList =
            ColorStateList.valueOf(
                MaterialColors.harmonizeWithPrimary(requireContext(), ContextCompat.getColor(requireContext(), R.color.minus_color))
            )

        val halfScreenHeight = displayMetrics.heightPixels * 0.62

        standardBottomSheetBehavior.peekHeight = halfScreenHeight.toInt()

        presetRecyclerView.adapter = HomePresetAdapter(
            mutableListOf(
                HolderTypes.PresetType(
                    null,
                    "Starbucks",
                    50f,
                    isBlock = false
                ),
                HolderTypes.PresetType(
                    isBlock = true
                )
            ),
            requireContext()
        )
        presetRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        chipRecyclerView.adapter = HomeChipAdapter(
            mutableListOf(
                HolderTypes.ChipType(
                ),
                HolderTypes.ChipType(
                )
            ),
            requireContext()
        )
        chipRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        return rootView
    }

}