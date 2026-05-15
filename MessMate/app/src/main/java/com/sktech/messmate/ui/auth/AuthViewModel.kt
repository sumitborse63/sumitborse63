package com.sktech.messmate.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sktech.messmate.data.model.User
import com.sktech.messmate.data.model.UserRole
import com.sktech.messmate.data.repository.AuthRepository
import com.sktech.messmate.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val user: User? = null,
    val error: String? = null,
    val needsRoleSelection: Boolean = false
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        checkAuthState()
    }

    private fun checkAuthState() {
        viewModelScope.launch {
            val firebaseUser = authRepository.currentUser
            if (firebaseUser != null) {
                _authState.update { it.copy(isLoading = true) }
                val result = userRepository.getUserOnce(firebaseUser.uid)
                result.onSuccess { user ->
                    if (user != null && user.getUserRole() != UserRole.NONE) {
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                isLoggedIn = true,
                                user = user,
                                needsRoleSelection = false
                            )
                        }
                    } else if (user != null) {
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                isLoggedIn = true,
                                user = user,
                                needsRoleSelection = true
                            )
                        }
                    } else {
                        // Firebase user exists but no Firestore doc - create it
                        val newUser = User(
                            uid = firebaseUser.uid,
                            name = firebaseUser.displayName ?: "",
                            email = firebaseUser.email ?: ""
                        )
                        userRepository.createUser(newUser)
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                isLoggedIn = true,
                                user = newUser,
                                needsRoleSelection = true
                            )
                        }
                    }
                }
                result.onFailure { e ->
                    _authState.update {
                        it.copy(isLoading = false, error = e.message)
                    }
                }
            }
        }
    }

    fun signInWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _authState.update { it.copy(isLoading = true, error = null) }
            val result = authRepository.signInWithEmail(email, password)
            result.onSuccess { firebaseUser ->
                val userResult = userRepository.getUserOnce(firebaseUser.uid)
                val user = userResult.getOrNull()
                if (user != null && user.getUserRole() != UserRole.NONE) {
                    _authState.update {
                        it.copy(isLoading = false, isLoggedIn = true, user = user)
                    }
                } else {
                    _authState.update {
                        it.copy(
                            isLoading = false,
                            isLoggedIn = true,
                            user = user,
                            needsRoleSelection = true
                        )
                    }
                }
            }
            result.onFailure { e ->
                _authState.update {
                    it.copy(isLoading = false, error = e.message)
                }
            }
        }
    }

    fun signUpWithEmail(name: String, email: String, password: String) {
        viewModelScope.launch {
            _authState.update { it.copy(isLoading = true, error = null) }
            val result = authRepository.signUpWithEmail(email, password)
            result.onSuccess { firebaseUser ->
                val user = User(
                    uid = firebaseUser.uid,
                    name = name,
                    email = email
                )
                userRepository.createUser(user)
                _authState.update {
                    it.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        user = user,
                        needsRoleSelection = true
                    )
                }
            }
            result.onFailure { e ->
                _authState.update {
                    it.copy(isLoading = false, error = e.message)
                }
            }
        }
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _authState.update { it.copy(isLoading = true, error = null) }
            val result = authRepository.signInWithGoogle(idToken)
            result.onSuccess { firebaseUser ->
                val exists = userRepository.userExists(firebaseUser.uid)
                if (exists) {
                    val userResult = userRepository.getUserOnce(firebaseUser.uid)
                    val user = userResult.getOrNull()
                    if (user != null && user.getUserRole() != UserRole.NONE) {
                        _authState.update {
                            it.copy(isLoading = false, isLoggedIn = true, user = user)
                        }
                    } else {
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                isLoggedIn = true,
                                user = user,
                                needsRoleSelection = true
                            )
                        }
                    }
                } else {
                    val user = User(
                        uid = firebaseUser.uid,
                        name = firebaseUser.displayName ?: "",
                        email = firebaseUser.email ?: "",
                        profileImageUrl = firebaseUser.photoUrl?.toString() ?: ""
                    )
                    userRepository.createUser(user)
                    _authState.update {
                        it.copy(
                            isLoading = false,
                            isLoggedIn = true,
                            user = user,
                            needsRoleSelection = true
                        )
                    }
                }
            }
            result.onFailure { e ->
                _authState.update {
                    it.copy(isLoading = false, error = e.message)
                }
            }
        }
    }

    fun selectRole(role: UserRole) {
        viewModelScope.launch {
            _authState.update { it.copy(isLoading = true) }
            val uid = authRepository.currentUser?.uid ?: return@launch
            val result = userRepository.updateUserRole(uid, role)
            result.onSuccess {
                _authState.update {
                    it.copy(
                        isLoading = false,
                        user = it.user?.copy(role = role.name),
                        needsRoleSelection = false
                    )
                }
            }
            result.onFailure { e ->
                _authState.update {
                    it.copy(isLoading = false, error = e.message)
                }
            }
        }
    }

    fun signOut() {
        authRepository.signOut()
        _authState.update {
            AuthState()
        }
    }

    fun clearError() {
        _authState.update { it.copy(error = null) }
    }
}
