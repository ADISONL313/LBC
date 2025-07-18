<template>
    <div class="logo-container">
        <h2 class="logo-title">LBC（Left-Behind-Children）<img src="../assets/user/logo.png" class="logo-img" /></h2>
    </div>
</template>

<style scoped>
.logo-container {
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: rgba(148.6, 212.3, 117.1, 0.1);
    border-radius: 8px;
    box-shadow: 0 0 8px rgba(148.6, 212.3, 117.1, 0.3);
    transition: background-color 0.3s ease;
    padding: 10px;
}

.logo-container:hover {
    background-color: rgba(148.6, 212.3, 117.1, 0.2);
}

.logo-title {
    margin: 0;
    font-family: 'Arial Black', sans-serif;
    font-size: 25px;
    color: black;
    text-shadow: 1px 1px 3px rgba(148.6, 212.3, 117.1, 0.5);
    position: relative;
    display: flex;
    align-items: center;
}

.logo-title::after {
    content: '';
    position: absolute;
    bottom: -3px;
    left: 0;
    width: 0;
    height: 2px;
    background-color: rgb(148.6, 212.3, 117.1);
    transition: width 0.3s ease;
}

.logo-container:hover .logo-title::after {
    width: 100%;
}

.logo-img {
    height: 40px;
    margin-left: 12px;
    filter: drop-shadow(1px 1px 3px rgba(148.6, 212.3, 117.1, 0.5));
    transition: transform 0.3s ease;
}

.logo-img:hover {
    transform: scale(1.05);
}
</style>