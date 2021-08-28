package hu.ahomolya.androidbase.provider

import hu.ahomolya.androidbase.networking.contract.SecretsProvider

class SecretsProviderImpl : SecretsProvider {
    external override fun getClientId(): String
}