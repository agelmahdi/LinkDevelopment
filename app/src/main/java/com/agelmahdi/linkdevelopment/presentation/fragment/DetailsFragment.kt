package com.agelmahdi.linkdevelopment.presentation.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.agelmahdi.linkdevelopment.R
import com.agelmahdi.linkdevelopment.util.Utils
import com.agelmahdi.linkdevelopment.databinding.FragmentDetailsBinding
import com.agelmahdi.linkdevelopment.domain.model.Article
import com.bumptech.glide.Glide

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null

    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navHostFragment.findNavController().run {
            binding.toolbar.setupWithNavController(this, AppBarConfiguration(graph))
            binding.toolbar.inflateMenu(R.menu.main)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val article = arguments?.getSerializable("article") as Article

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        Glide.with(binding.root).load(article.urlToImage).into(binding.ivImage)
        binding.tvTitle.text = article.title
        binding.tvDescription.text = article.description
        binding.tvAuthur.text = article.author
        binding.tvDate.text = Utils.formatDate(article.publishedAt)
        binding.btnWeb.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(article.url)
            requireActivity().startActivity(intent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}