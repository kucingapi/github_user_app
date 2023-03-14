package com.example.githubuser.api

import com.google.gson.annotations.SerializedName

data class ResponseFollow(

	@field:SerializedName("Follow")
	val follow: List<User?>? = null
)

