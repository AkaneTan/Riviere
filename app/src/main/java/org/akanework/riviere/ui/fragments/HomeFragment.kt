package org.akanework.riviere.ui.fragments

import android.animation.ValueAnimator
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.text.InputFilter
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.color.MaterialColors
import com.google.android.material.slider.Slider
import org.akanework.riviere.R
import org.akanework.riviere.logic.px
import org.akanework.riviere.logic.utils.DecimalDigitsInputFilter
import org.akanework.riviere.ui.adapters.HomeChipAdapter
import org.akanework.riviere.ui.adapters.HomePresetAdapter
import org.akanework.riviere.ui.data.HolderTypes


class HomeFragment : BaseFragment() {

    companion object {
        const val ACTION_BUTTON_ANIMATION_DURATION: Long = 300
    }

    private lateinit var prefs: SharedPreferences

    private lateinit var formatSwitchButton: MaterialButton
    private lateinit var targetExpenseEditText: EditText

    private var colorPlusPrimaryContainer: Int = -1
    private var colorPlusOnPrimaryContainer: Int = -1
    private var colorMinusPrimaryContainer: Int = -1
    private var colorMinusOnPrimaryContainer: Int = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setBackgroundColor(
            MaterialColors.getColor(view, com.google.android.material.R.attr.colorSurfaceContainer)
        )
    }

    @Suppress("DEPRECATION")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("TAG", "HOMEFRAGMENT ONCREATE")

        prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        // Upper part
        val presetRecyclerView: RecyclerView = rootView.findViewById(R.id.preset_recyclerview)
        val chipRecyclerView: RecyclerView = rootView.findViewById(R.id.chips_recyclerview)
        targetExpenseEditText = rootView.findViewById(R.id.target_expense)

        formatSwitchButton = rootView.findViewById(R.id.plus_minus_switch)

        // Bottom part
        val bottomSheet: FrameLayout = rootView.findViewById(R.id.bottom_sheet)
        val standardBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        val bottomSheetHeaderCard: MaterialCardView = bottomSheet.findViewById(R.id.bottom_header)
        val expenseTitleTextView: TextView = bottomSheet.findViewById(R.id.expense_title)
        val currencyIcon: TextView = bottomSheet.findViewById(R.id.currency)
        val roundCounterTextView: TextView = bottomSheet.findViewById(R.id.round)
        val decimalCounterTextView: TextView = bottomSheet.findViewById(R.id.decimal)
        val incomeTitleTextView: TextView = bottomSheet.findViewById(R.id.income_title)
        val incomeTextView: TextView = bottomSheet.findViewById(R.id.income)
        val budgetTitleTextView: TextView = bottomSheet.findViewById(R.id.budget_title)
        val budgetTextView: TextView = bottomSheet.findViewById(R.id.budget)
        val budgetSlider: Slider = bottomSheet.findViewById(R.id.budget_slider)

        // Get colors
        val colorPositiveBottomHeaderPrimaryContainer =
            MaterialColors.harmonizeWithPrimary(
                requireContext(),
                ContextCompat.getColor(
                    requireContext(),
                    R.color.bottom_sheet_colorPrimaryContainer)
            )
        val colorPositiveBottomHeaderOnPrimaryContainer =
            MaterialColors.harmonizeWithPrimary(
                requireContext(),
                ContextCompat.getColor(
                    requireContext(),
                    R.color.bottom_sheet_colorOnPrimaryContainer)
            )
        val colorPositiveBottomHeaderPrimaryInverse =
            MaterialColors.harmonizeWithPrimary(
                requireContext(),
                ContextCompat.getColor(
                    requireContext(),
                    R.color.bottom_sheet_colorPrimaryInverse)
            )
        colorPlusPrimaryContainer =
            MaterialColors.getColor(
                requireContext(),
                com.google.android.material.R.attr.colorPrimary,
                -1
            )
        colorPlusOnPrimaryContainer =
            MaterialColors.getColor(
                requireContext(),
                com.google.android.material.R.attr.colorOnPrimary,
                -1
            )
        colorMinusPrimaryContainer =
            MaterialColors.getColor(
                requireContext(),
                com.google.android.material.R.attr.colorTertiary,
                -1
            )
        colorMinusOnPrimaryContainer =
            MaterialColors.getColor(
                requireContext(),
                com.google.android.material.R.attr.colorOnTertiary,
                -1
            )

        setUpSwitchButton()
        setupUpperPart()

        // Dispatch colors
        bottomSheetHeaderCard.setCardBackgroundColor(
            colorPositiveBottomHeaderPrimaryContainer
        )
        expenseTitleTextView.setTextColor(
            colorPositiveBottomHeaderOnPrimaryContainer
        )
        currencyIcon.setTextColor(
            colorPositiveBottomHeaderOnPrimaryContainer
        )
        roundCounterTextView.setTextColor(
            colorPositiveBottomHeaderOnPrimaryContainer
        )
        decimalCounterTextView.setTextColor(
            colorPositiveBottomHeaderOnPrimaryContainer
        )
        incomeTitleTextView.setTextColor(
            colorPositiveBottomHeaderOnPrimaryContainer
        )
        incomeTextView.setTextColor(
            colorPositiveBottomHeaderOnPrimaryContainer
        )
        budgetTitleTextView.setTextColor(
            colorPositiveBottomHeaderOnPrimaryContainer
        )
        budgetTextView.setTextColor(
            colorPositiveBottomHeaderOnPrimaryContainer
        )
        budgetSlider.trackActiveTintList =
            ColorStateList.valueOf(colorPositiveBottomHeaderOnPrimaryContainer)
        budgetSlider.trackInactiveTintList =
            ColorStateList.valueOf(colorPositiveBottomHeaderPrimaryInverse)

        val displayMetrics = DisplayMetrics()

        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)

        val halfScreenHeight = displayMetrics.heightPixels - 324.px

        standardBottomSheetBehavior.peekHeight = halfScreenHeight

        presetRecyclerView.adapter = HomePresetAdapter(
            mutableListOf(
                HolderTypes.PresetType(
                    null,
                    "星巴克",
                    -50f,
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
                    R.drawable.ic_category_filled,
                    "默认"
                ),
                HolderTypes.ChipType(

                ),
                HolderTypes.ChipType(
                    isBlock = true
                )
            ),
            requireContext()
        )
        chipRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        return rootView
    }

    private fun setUpSwitchButton() {
        val formatSwitchButtonIsPlus = prefs.getBoolean("switch_button_state", true)
        setButtonStatus(formatSwitchButtonIsPlus, false)
        formatSwitchButton.setOnClickListener {
            val status = prefs.getBoolean("switch_button_state", true)
            setButtonStatus(
                !status, true
            )
            prefs.edit()
                .putBoolean("switch_button_state", !status)
                .apply()
        }
    }

    private fun setButtonStatus(isPlus: Boolean, withAnimation: Boolean) {
        if (!withAnimation) {
            formatSwitchButton.iconTint =
                ColorStateList.valueOf(
                    if (isPlus) colorPlusOnPrimaryContainer else colorMinusOnPrimaryContainer
                )
            formatSwitchButton.backgroundTintList =
                ColorStateList.valueOf(
                    if (isPlus) colorPlusPrimaryContainer else colorMinusPrimaryContainer
                )
            formatSwitchButton.icon =
                AppCompatResources.getDrawable(
                    requireContext(),
                    if (isPlus) R.drawable.minus_to_plus else R.drawable.plus_to_minus
                )
        } else {
            val backgroundAnimator = ValueAnimator.ofArgb(
                if (isPlus) colorMinusPrimaryContainer else colorPlusPrimaryContainer,
                if (isPlus) colorPlusPrimaryContainer else colorMinusPrimaryContainer
            )
            val iconAnimator = ValueAnimator.ofArgb(
                if (isPlus) colorMinusOnPrimaryContainer else colorPlusOnPrimaryContainer,
                if (isPlus) colorPlusOnPrimaryContainer else colorMinusOnPrimaryContainer
            )
            backgroundAnimator.apply {
                addUpdateListener {
                    val color = it.animatedValue as Int
                    formatSwitchButton.backgroundTintList =
                        ColorStateList.valueOf(color)
                }
                duration = ACTION_BUTTON_ANIMATION_DURATION
            }
            iconAnimator.apply {
                addUpdateListener {
                    val color = it.animatedValue as Int
                    formatSwitchButton.iconTint =
                        ColorStateList.valueOf(color)
                }
                duration = ACTION_BUTTON_ANIMATION_DURATION
            }
            formatSwitchButton.icon =
                AppCompatResources.getDrawable(
                    requireContext(),
                    if (isPlus) R.drawable.minus_to_plus else R.drawable.plus_to_minus
                )
            backgroundAnimator.start()
            iconAnimator.start()
            (formatSwitchButton.icon as AnimatedVectorDrawable).start()
        }
    }

    private fun setupUpperPart() {
        targetExpenseEditText.setFilters(
            arrayOf<InputFilter>(
                DecimalDigitsInputFilter(2)
            )
        )
    }

}