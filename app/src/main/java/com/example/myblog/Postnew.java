    package com.example.myblog;

    public class Postnew {

        private String postId;
        private String postTitle;
        private String description;
        private String imageUri;

        public Postnew(){

        }

        public Postnew(String postId, String postTitle, String description, String imageUri) {
            this.postId = postId;
            this.postTitle = postTitle;
            this.description = description;
            this.imageUri = imageUri;
        }

        public String getPostId() {
            return postId;
        }

        public void setPostId(String postId) {
            this.postId = postId;
        }

        public String getPostTitle() {
            return postTitle;
        }

        public void setPostTitle(String postTitle) {
            this.postTitle = postTitle;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImageUri() {
            return imageUri;
        }

        public void setImageUri(String imageUri) {
            this.imageUri = imageUri;
        }
    }
